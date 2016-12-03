package com.ibm.spe.comparetool4.bigxlsx;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * 用于读取大的xlsx文件，可以占用比较少的内存
 * <p>所有读取的单元格数据将保存为字符数据和数值数据</P>
 * <p>对大数据量的xlsx处理,使用event model方式, xlsx是已xml形式来组织excel的， 
 * 这种方式用了java处理xml的流的方式，SAXPaser，能占用更少的内存，避免内存溢出 </p>
 * @author Luis
 */
public class SpeLargeXlsxParser {
	private String fileName;
	private OPCPackage opcPackage;
	private XSSFReader xssfReader;
	private XMLReader xmlParser;	
	private List<String> sheetIds = new ArrayList<String>();
	private Map<String, String> sheetNameId = new HashMap<String, String>();
	
	private Sheet sheet;
	private Row row;
	private Cell cell;
	
	public SpeLargeXlsxParser(String fileName) throws Exception {
		this.fileName = fileName;				
	}
	
	public void init() throws Exception {
		opcPackage = OPCPackage.open(fileName);
		xssfReader = new XSSFReader(opcPackage);
		SharedStringsTable sst = xssfReader.getSharedStringsTable();
		xmlParser = fetchSheetParser(sst);		
	}
	
	public void close() throws Exception {
		opcPackage.close();
		opcPackage = null;
		xssfReader = null;		
		xmlParser = null;
		sheetIds.clear();
		sheetIds = null;
		sheetNameId.clear();
		sheetNameId = null;
	}
	
	public XMLReader fetchSheetParser(SharedStringsTable sst) throws Exception {
		XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		ContentHandler handler = new SheetHandler(sst);
		parser.setContentHandler(handler);
		return parser;
	}
	
	public Map<String, Sheet> getAllSheets() throws Exception {
		Map<String, Sheet> speWorkbook = new HashMap<String, Sheet>();		
		parseWorkbook();		
		for(String sheetId : sheetIds) {
			sheet = new SpeSheet();
			speWorkbook.put(sheetNameId.get(sheetId), sheet);
			InputStream sheet = xssfReader.getSheet(sheetId);
			InputSource sheetSource = new InputSource(sheet);
			xmlParser.parse(sheetSource);
			sheet.close();			
		}		
		return speWorkbook;
	} 
	
	public void parseWorkbook() throws Exception {
		InputStream sheets = xssfReader.getWorkbookData();
		InputSource sheetSource = new InputSource(sheets);
		xmlParser.parse(sheetSource);
		sheets.close();
	}
	
	private class SheetHandler extends DefaultHandler {
		private SharedStringsTable sst;
		private String lastContents;
		private int cellType;
		
		private SheetHandler(SharedStringsTable sst) {
			this.sst = sst;
		}		

		public void startElement(String uri, String localName, String name,	Attributes attributes) throws SAXException {
			if("sheet".equals(name)) {
				String sheetName = attributes.getValue("name");
				String sheetId = attributes.getValue("r:id");
				sheetNameId.put(sheetId, sheetName);
				sheetIds.add(sheetId);				 			
			}
			if("row".equals(name)) {				
				String rowNum = attributes.getValue("r");				
				row = sheet.createRow(Integer.valueOf(rowNum) - 1);							
			}
			if("c".equals(name)) {								
				// Print the cell reference, r表示单元格的位置如A5
				String reference = attributes.getValue("r");				 
				// Figure out if the value is an index in the SST, t表示单元格值得类型s为string字符型
				String cellValueType = attributes.getValue("t");
				int columnIndex= reference.charAt(0) - 'A';
				if(reference.charAt(1) > '9') {
					columnIndex = 26 + reference.charAt(1) - 'A';
				}
				cell = row.createCell(columnIndex);
//				nextIsString = true;
				if(cellValueType != null && "s".equals(cellValueType)) {					
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cellType = Cell.CELL_TYPE_STRING;
				} else {		
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cellType = Cell.CELL_TYPE_NUMERIC;
				}
			}
			// Clear contents cache
			lastContents = "";
		}
		
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			if(name.equals("v")) {
				if(cellType == Cell.CELL_TYPE_STRING) {
					int idx = Integer.parseInt(lastContents);
					lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();	
					cell.setCellValue(lastContents);
				} else if(cellType == Cell.CELL_TYPE_NUMERIC) {
					try {
						cell.setCellValue(Double.valueOf(lastContents));
					} catch(Exception e) {
						cell.setCellValue(lastContents);
						System.err.println(lastContents + "不是数字类型");	
					}					
				} else {
					System.err.println("未处理情形");
				}				
			}
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
			lastContents += new String(ch, start, length);
		}
	}
}
