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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ibm.spe.comparetool4.bigxlsx.SpeWorkbook;
import com.ibm.spe.comparetool4.config.ColumnMapping;
import com.ibm.spe.comparetool4.config.SheetMapping;
import com.ibm.spe.comparetool4.gui.ConsoleTextarea;
import com.ibm.spe.comparetool4.gui.MessageDisplay;
import com.ibm.spe.comparetool4.util.ConfigFileHelper;

/**
 * Created by luis on 2015/2/5.
 */
public class FinancialFile extends CompareFileAdapter {
    private static Log logger = LogFactory.getLog(FinancialFile.class);

    private Map<String, Map<String, Row>> sheets = new HashMap<String, Map<String, Row>>();
    private Map<String, Row> titleRows = new HashMap<String, Row>();
    private Map<String, Integer> sheetRowCounts = new HashMap<String, Integer>();
    private String fileName;

    private static List<String> specialSheetName = new ArrayList<String>();
    static {
        specialSheetName.add("FINANCIAL_FACTORS");
        specialSheetName.add("UNIT_COST");
        specialSheetName.add("LIST_PRICE_AND_DELEGATION");
        specialSheetName.add("MARKET_REF_PRICE");
    }

    public FinancialFile(String fileName) {
        this.fileName = fileName;
        long startTime = System.currentTimeMillis();
        ConsoleTextarea.info("Loading financial file : " + fileName);
        init(fileName);
        ConsoleTextarea.info("Complete loading, total time " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private void init(String file) {
        InputStream in = null;
        Workbook wb;
        try {
            if(isBigFile(new File(file))) {
                wb = new SpeWorkbook(file);
            } else {
                in = new FileInputStream(file);
                wb = WorkbookFactory.create(in);
            }
            Set<String> sheetNames = getAllFinancialSheets();
            for (String sheetName : sheetNames) {
                Sheet sheet = wb.getSheet(sheetName);
                if (sheet != null) {
                    Map<String, Row> rowMap = new HashMap<String, Row>();
                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) {
                            titleRows.put(sheetName, row);
                        } else {
                            String rowKey = generateKey(sheetName, row);
                            rowMap.put(rowKey, row);
                        }
                    }
                    sheets.put(sheetName, rowMap);
                    sheetRowCounts.put(sheetName, sheet.getLastRowNum());
                }
            }
        } catch (Exception e) {
            MessageDisplay.show(e.getMessage());
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

    private Set<String> getAllFinancialSheets() {
        Set<String> sheetNames = new HashSet<String>();
        List<SheetMapping> sheets = ConfigFileHelper.getConfig().getFfSheetMappings();
        for (SheetMapping mapping : sheets) {
            sheetNames.add(mapping.getSheet1Name());
        }
        sheets = ConfigFileHelper.getConfig().getFmSheetMappings();
        for (SheetMapping mapping : sheets) {
            sheetNames.add(mapping.getSheet1Name());
        }
        return sheetNames;
    }

    private String generateKey(String sheetName, Row row) throws CompareException {

        if (specialSheetName.contains(sheetName)) {
            int countryColumnNumber = getColumnIndex(sheetName, "Country");
            int offeringColumnNumber = getColumnIndex(sheetName, "Offering");
            int idColumnNumber = getColumnIndex(sheetName, "ID");
            int featureCodeColumnNumber = getColumnIndex(sheetName, "Feature Code");

            String country = getCellValue(row.getCell(countryColumnNumber));
            String countryCode = ConfigFileHelper.getConfig().getCountryCode(country);
            if(countryCode == null) {
                throw new CompareException(String.format("Not found country code for country[%s] ", country));
            }
            return countryCode + getCellValue(row.getCell(offeringColumnNumber)) + row.getCell(idColumnNumber).getStringCellValue() + getCellValue(row.getCell(featureCodeColumnNumber));
        } else {
            return getCellValue(row.getCell(0));
        }
    }

    @Override
    public Map<String, Row> getSheetRows(String sheetName) throws CompareException {
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
            if (getCellValue(cell).trim().equalsIgnoreCase(columnName)) {
                return cell.getColumnIndex();
            }
        }
        // exception
        throw new CompareException(String.format("Error, Not found column[%s] on sheet[%s] for the financial file", columnName, sheetName));
    }

    @Override
    public List<ColumnMapping> getAllColumnForSheet(String sheetName) {
        List<ColumnMapping> columnMappings = new ArrayList<ColumnMapping>();
        Row row = titleRows.get(sheetName);
        for(Cell cell : row) {
            ColumnMapping columnMapping = new ColumnMapping();
            String cellValue = getCellValue(cell);
            columnMapping.setSheet1Name(sheetName);
            columnMapping.setSheet1ColumnName(cellValue);
            columnMapping.setSheet2Name(sheetName);
            columnMapping.setSheet2ColumnName(cellValue);
            columnMapping.setColumnNeedCompare(true);
            columnMappings.add(columnMapping);
        }
        return columnMappings;
    }

    /**
     * USG to US
     * @return
     */
    public static String convertKey(String key) {
    	if(key.length() >= 3) {
    		if("USG".equals(key.substring(0,3))) {
                key = "US" + key.substring(3);
            }
    	}
        return key;
    }
    
    @Override
    public int getSheetRowCount(String sheetName) {
    	return sheetRowCounts.get(sheetName);
    }
}
