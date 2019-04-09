package com.ibm.spe.comparetool4.compare;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ibm.spe.comparetool4.bigxlsx.SpeWorkbook;
import com.ibm.spe.comparetool4.config.ColumnMapping;
import com.ibm.spe.comparetool4.config.SheetMapping;
import com.ibm.spe.comparetool4.gui.ConsoleTextarea;
import com.ibm.spe.comparetool4.util.ConfigFileHelper;

/**
 * Created by luis on 2015/2/5.
 */
public class MrtFile extends CompareFileAdapter {
    private static Log logger = LogFactory.getLog(MrtFile.class);

    private Map<String, Map<String, Row>> sheets = new HashMap<String, Map<String, Row>>();
    private Map<String, Row> titleRows = new HashMap<String, Row>();
    private Map<String, Integer> sheetRowCounts = new HashMap<String, Integer>();
    private static Map<String, List<String>> sheetSeparateKeys = new HashMap<String, List<String>>();
    private String fileName;
    private static List<String> specialSheetName = new ArrayList<String>();
    private Map<String, String> soItsMapping;
    
    private String compareType = CompareTest.MM;
    
    static {
        specialSheetName.add("STD_FACTORS");
        specialSheetName.add("STD_RATE");
        specialSheetName.add("LIST_PRICE_N");
        specialSheetName.add("MKT_PRICE");
    }

    public MrtFile(String fileName) {
    	soItsMapping = ConfigFileHelper.getConfig().getSoItsMappings();
        this.fileName = fileName;
        long startTime = System.currentTimeMillis();
        ConsoleTextarea.info("Loading mrt file : " + fileName);
        init(fileName);
        ConsoleTextarea.info("Complete loading, total time " + (System.currentTimeMillis() - startTime) + "ms");
    }
    
    public MrtFile(String fileName, String compareType) {
    	this.compareType = compareType;
    	soItsMapping = ConfigFileHelper.getConfig().getSoItsMappings();
        this.fileName = fileName;
        long startTime = System.currentTimeMillis();
        ConsoleTextarea.info("Loading mrt file : " + fileName);
        init(fileName);
        ConsoleTextarea.info("Complete loading, total time " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private void init(String file) {
        InputStream in = null;
        Workbook wb;
        try {
            if(isBigFile(new File(file))) {
                wb =  new SpeWorkbook(file);
            } else {
                in = new FileInputStream(file);
                wb = WorkbookFactory.create(in);
            }
            Set<String> sheetNames = getAllMrtSheets();
            for (String sheetName : sheetNames) {
//            	System.out.println(sheetName);
                Sheet sheet = wb.getSheet(sheetName);
//                System.out.println(sheet == null);
                if (sheet == null) {
                	continue;
                }
                sheetRowCounts.put(sheetName, sheet.getLastRowNum());
                List<String> separatedKeys = new ArrayList<String>();
                sheetSeparateKeys.put(sheetName, separatedKeys);
                if (sheet != null) {
                    Map<String, Row> rowMap = new HashMap<String, Row>();
                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) {
                            titleRows.put(sheetName, row);
                        } else {
//                        	System.out.println(sheetName + row.getRowNum());
                            String[] rowKeys = generateKey(sheetName, row);
                            rowMap.put(rowKeys[0], row);
                            rowMap.put(rowKeys[1], row);
                            if (rowKeys[0] == null) {
                            	System.out.println("出错：" + sheetName + " row " + row.getRowNum());
                            	throw new Exception("出错：" + sheetName + " row " + row.getRowNum());
                            }
                            if (!rowKeys[0].equals(rowKeys[1])) {
                            	separatedKeys.add(rowKeys[0]);
                            	separatedKeys.add(rowKeys[1]);
                            }
                        }
                    }
                    sheets.put(sheetName, rowMap);
//                    System.out.println(sheetName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Set<String> getAllMrtSheets() {
        Set<String> sheetNames = new HashSet<String>();
        List<SheetMapping> sheets = ConfigFileHelper.getConfig().getMmSheetMappings();
        for (SheetMapping mapping : sheets) {
            sheetNames.add(mapping.getSheet1Name());
        }
        sheets = ConfigFileHelper.getConfig().getFmSheetMappings();
        for (SheetMapping mapping : sheets) {
            sheetNames.add(mapping.getSheet2Name());
        }
        return sheetNames;
    }

    /**
     * IT~6948-85D~6943-A31~7082~2013~IBM转化成
     * IT694885D7082 和 IT6943A317082
     * @param sheetName
     * @param row
     * @return
     */
    private String[] generateKey(String sheetName, Row row) {
        String[] keys = new String[2];
        String cellValue = getCellValue(row.getCell(0));
//        System.out.println(cellValue);
        if (specialSheetName.contains(sheetName)) {
            String[] s = cellValue.split("~");
            if(s.length >=4) {
                String[] s1 = convertSoIdToItsId(s[1]).split("-");
                keys[0] = s[0] + s1[0] + s1[1] + s[3];
                String[] s2 = convertSoIdToItsId(s[2]).split("-");
                keys[1] = s[0] + s2[0] + s2[1] + s[3];
            }
            // US~6943-02K~6943-02K~9646~2015~SFC
        } else {
            keys[0] = cellValue;
            keys[1] = keys[0];
        }
        if (keys[0] == null) {
        	System.out.println("出错：" + sheetName);
        }
        logger.debug(String.format("Convert key[%s] to key[%s] for sheet[%s]", cellValue, keys, sheetName));
        return keys;
    }
    
    private String convertSoIdToItsId(String soId) {
    	String itsId = soId;
    	if (CompareTest.MM.equals(compareType)) {
    		String tmp = soItsMapping.get(soId);
    		if (tmp != null) {
    			itsId = tmp;
//    			System.out.println(itsId);
    		}
    	}
    	return itsId;
    }

    @Override
    public Map<String, Row> getSheetRows(String sheetName) throws CompareException{
        Map<String, Row> rows = sheets.get(sheetName);
        if(rows != null) {
            return rows;
        }
        throw new CompareException(String.format("Not found sheet[%s] in file[%s]", sheetName, fileName));
    }

    @Override
    public int getColumnIndex(String sheetName, String columnName) throws CompareException {
        Row row = titleRows.get(sheetName);
        for (Cell cell : row) {
            if (getCellValue(cell).equals(columnName)) {
                return cell.getColumnIndex();
            }
        }
        // exception
        String exceptionMessage = String.format("Error, Not found column[%s] on sheet[%s] for the mrt file", columnName, sheetName);
        throw new CompareException(exceptionMessage);
    }

    @Override
    public List<ColumnMapping> getAllColumnForSheet(String sheetName) {
        List<ColumnMapping> columnMappings = new ArrayList<ColumnMapping>();
        Row row = titleRows.get(sheetName);
        for(Cell cell : row) {
            ColumnMapping columnMapping = new ColumnMapping();
            String cellValue = getCellValue(cell);
           	if ("LOOKUP_VALUE".equals(cellValue)) {
        		continue;
        	}
            columnMapping.setSheet1Name(sheetName);
            columnMapping.setSheet1ColumnName(cellValue);
            columnMapping.setSheet2Name(sheetName);
            columnMapping.setSheet2ColumnName(cellValue);
            columnMapping.setColumnNeedCompare(true);
            columnMappings.add(columnMapping);
        }
        return columnMappings;
    }
    
    @Override
    public int getSheetRowCount(String sheetName) {
    	return sheetRowCounts.get(sheetName);
    }
    
    public static boolean isSeperatedKey(String sheetName, String key) {
    	return sheetSeparateKeys.get(sheetName).contains(key);
    }
}
