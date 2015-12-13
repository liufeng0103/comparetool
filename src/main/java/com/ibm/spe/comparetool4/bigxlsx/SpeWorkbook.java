package com.ibm.spe.comparetool4.bigxlsx;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * 用于表示一个操作的excel文件
 * @author Luis
 */
public class SpeWorkbook extends SpeWorkbookAdapter {
	private Map<String, Sheet> sheets = new HashMap<String, Sheet>();

	public SpeWorkbook(String fileName) throws Exception {
		SpeLargeXlsxParser xlsxParser = new SpeLargeXlsxParser(fileName);
		xlsxParser.init();
		sheets = xlsxParser.getAllSheets();
		xlsxParser.close();
	}
	
	@Override
	public Sheet createSheet(String sheetName) {
		Sheet sheet = new SpeSheet();		
		sheets.put(sheetName, sheet);
		return sheet;
	}

	@Override
	public Sheet getSheet(String sheetName) {		
		return sheets.get(sheetName);
	}

}
