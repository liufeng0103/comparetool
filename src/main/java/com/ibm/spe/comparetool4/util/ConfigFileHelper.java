package com.ibm.spe.comparetool4.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ibm.spe.comparetool4.config.ColumnMapping;
import com.ibm.spe.comparetool4.config.CompareConfig;
import com.ibm.spe.comparetool4.config.CountryMapping;
import com.ibm.spe.comparetool4.config.FFSheetMapping;
import com.ibm.spe.comparetool4.config.FMSheetMapping;
import com.ibm.spe.comparetool4.config.MMSheetMapping;
import com.ibm.spe.comparetool4.config.SheetMapping;
import com.ibm.spe.comparetool4.gui.MessageDisplay;

/**
 * Created by luis on 2015/2/3.
 */
public class ConfigFileHelper {

    private static Log logger = LogFactory.getLog(ConfigFileHelper.class);

    private static final String CONFIG_FILE_NAME = "./config/CompareConfig.xls";
    private static final String COMMON_CONFIG = "CommonConfig";
    private static final String COUNTRY_MAPPING = "CountryMapping";
    private static final String FM_SHEET_MAPPING = "FMSheetMapping";
    private static final String FF_SHEET_MAPPING = "FFSheetMapping";
    private static final String MM_SHEET_MAPPING = "MMSheetMapping";
    private static final String COLUMN_MAPPING = "ColumnMapping";

    private static CompareConfig config = new CompareConfig();

    static {
        readConfig();
    }

    private static void readConfig() {
        try {
            Workbook wb = WorkbookFactory.create(new File(CONFIG_FILE_NAME));
            config.setCountryMappings(readCountryMapping(wb));
            config.setCommonConfigs(readCommonConfig(wb));
            config.setFmSheetMappings(readFMSheetMapping(wb));
            config.setFfSheetMappings(readFFSheetMapping(wb));
            config.setMmSheetMappings(readMMSheetMapping(wb));
            config.setColumnMappings(readColumnMapping(wb));
        } catch (Exception e) {
            MessageDisplay.show(e.getMessage());
            logger.error(e);
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        InputStream in = null;
        OutputStream fileOut = null;
        try {
            in = new FileInputStream(CONFIG_FILE_NAME);
            Workbook wb = WorkbookFactory.create(in);
            writeCommonConfig(wb);
            writeFFSheetMapping(wb);
            writeFMSheetMapping(wb);
            writeMMSheetMapping(wb);
            writeColumnMapping(wb);
            fileOut = new FileOutputStream(CONFIG_FILE_NAME);
            wb.write(fileOut);
        } catch (Exception e) {
                MessageDisplay.show(e.getMessage());
                logger.error(e);
                e.printStackTrace();
        } finally {
            try {
                if(in != null) {
                    in.close();
                }
                if(fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeCommonConfig(Workbook wb) {
        Sheet sheet = wb.getSheet(COMMON_CONFIG);
        sheet.getRow(0).createCell(1).setCellValue(config.getFileChooseDefaultPath());
        sheet.getRow(1).createCell(1).setCellValue(config.getResultFileName());
    }

    private static Map<String, String> readCommonConfig(Workbook wb) {
        Map<String, String> commonConfigs = new HashMap<String, String>();
        Sheet sheet = wb.getSheet(COMMON_CONFIG);
        for(Row row : sheet) {
            commonConfigs.put(getCellValue(row.getCell(0)), getCellValue(row.getCell(1)));
        }
        return commonConfigs;
    }

    private static List<CountryMapping> readCountryMapping(Workbook wb) {
        Sheet sheet = wb.getSheet(COUNTRY_MAPPING);
        List<CountryMapping> mappings = new ArrayList<CountryMapping>();
        for(Row row : sheet) {
            CountryMapping mapping = new CountryMapping();
            String countryCode = row.getCell(0).getStringCellValue();
            String country = row.getCell(1).getStringCellValue();
            mapping.setCountry(country);
            mapping.setCountryCode(countryCode);
            mappings.add(mapping);
        }
        return mappings;
    }

    public static void writeFMSheetMapping(Workbook wb) {
        Sheet sheet = wb.getSheet(FM_SHEET_MAPPING);
        List<SheetMapping> mappings = config.getFmSheetMappings();
        writeSheetMapping(sheet, mappings);
    }

    private static List<SheetMapping> readFMSheetMapping(Workbook wb) {
        Sheet sheet = wb.getSheet(FM_SHEET_MAPPING);
        List<SheetMapping> mappings = new ArrayList<SheetMapping>();
        for(Row row : sheet) {
            FMSheetMapping mapping = new FMSheetMapping();
            if (row.getRowNum() == 0) {
                Vector columnNames = new Vector();
                columnNames.add(row.getCell(0).getStringCellValue());
                columnNames.add(row.getCell(1).getStringCellValue());
                columnNames.add(row.getCell(2).getStringCellValue());
                config.setFmSheetMappingTableColumnNames(columnNames);
            } else {
                String financialSheetName = row.getCell(0).getStringCellValue();
                String mrtSheetName = row.getCell(1).getStringCellValue();
                boolean needCompare = false;
                if ("true".equalsIgnoreCase(row.getCell(2).getStringCellValue())) {
                    needCompare = true;
                }
                mapping.setFinancialSheetName(financialSheetName);
                mapping.setMrtSheetName(mrtSheetName);
                mapping.setNeedCompare(needCompare);
                mappings.add(mapping);
            }
        }
        return mappings;
    }

    public static void writeFFSheetMapping(Workbook wb) {
        Sheet sheet = wb.getSheet(FF_SHEET_MAPPING);
        List<SheetMapping> mappings = config.getFfSheetMappings();
        writeSheetMapping(sheet, mappings);
    }

    private static List<SheetMapping> readFFSheetMapping(Workbook wb) {
        Sheet sheet = wb.getSheet(FF_SHEET_MAPPING);
        List<SheetMapping> mappings = new ArrayList<SheetMapping>();
        for(Row row : sheet) {
            FFSheetMapping mapping = new FFSheetMapping();
            if(row.getRowNum() == 0) {
                Vector columnNames = new Vector();
                columnNames.add(row.getCell(0).getStringCellValue());
                columnNames.add(row.getCell(1).getStringCellValue());
                columnNames.add(row.getCell(2).getStringCellValue());
                config.setFfSheetMappingTableColumnNames(columnNames);
            } else {
                String financial1SheetName = row.getCell(0).getStringCellValue();
                String financial2SheetName = row.getCell(1).getStringCellValue();
                boolean needCompare = false;
                if("true".equalsIgnoreCase(getCellValue(row.getCell(2)))) {
                    needCompare = true;
                }
                mapping.setFinancial1SheetName(financial1SheetName);
                mapping.setFinancial2SheetName(financial2SheetName);
                mapping.setNeedCompare(needCompare);
                mappings.add(mapping);
            }
        }
        return mappings;
    }

    public static void writeMMSheetMapping(Workbook wb) {
        Sheet sheet = wb.getSheet(MM_SHEET_MAPPING);
        List<SheetMapping> mappings = config.getMmSheetMappings();
        writeSheetMapping(sheet, mappings);
    }

    private static void writeSheetMapping(Sheet sheet, List<SheetMapping> mappings) {
        for(int i = 1; i <= sheet.getLastRowNum(); i++) {
            if(sheet.getRow(i) != null) {
                sheet.removeRow(sheet.getRow(i));
            }
        }
        for(int i = 0; i < mappings.size(); i++) {
            SheetMapping mapping = mappings.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(mapping.getSheet1Name());
            row.createCell(1).setCellValue(mapping.getSheet2Name());
            row.createCell(2).setCellValue(String.valueOf(mapping.isSheetNeedCompare()));
        }
    }

    private static List<SheetMapping> readMMSheetMapping(Workbook wb) {
        Sheet sheet = wb.getSheet(MM_SHEET_MAPPING);
        List<SheetMapping> mappings = new ArrayList<SheetMapping>();
        for(Row row : sheet) {
            MMSheetMapping mapping = new MMSheetMapping();
            if(row.getRowNum() == 0) {
                Vector columnNames = new Vector();
                columnNames.add(row.getCell(0).getStringCellValue());
                columnNames.add(row.getCell(1).getStringCellValue());
                columnNames.add(row.getCell(2).getStringCellValue());
                config.setMmSheetMappingTableColumnNames(columnNames);
            } else {
                String mrt1SheetName = row.getCell(0).getStringCellValue();
                String mrt2SheetName = row.getCell(1).getStringCellValue();
                boolean needCompare = false;
                if("true".equalsIgnoreCase(row.getCell(2).getStringCellValue())) {
                    needCompare = true;
                }
                mapping.setMrt1SheetName(mrt1SheetName);
                mapping.setMrt2SheetName(mrt2SheetName);
                mapping.setNeedCompare(needCompare);
                mappings.add(mapping);
            }
        }
        return mappings;
    }

    public static void writeColumnMapping(Workbook wb) {
        Sheet sheet = wb.getSheet(COLUMN_MAPPING);
        List<ColumnMapping> columnMappings = config.getColumnMappings();
        for(int i = 1; i <= sheet.getLastRowNum(); i++) {
            if(sheet.getRow(i) != null) {
                sheet.removeRow(sheet.getRow(i));
            }
        }
        for(int i = 0; i < columnMappings.size(); i++) {
            ColumnMapping mapping = columnMappings.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(mapping.getSheet1Name());
            row.createCell(1).setCellValue(mapping.getSheet1ColumnName());
            row.createCell(2).setCellValue(mapping.getSheet2Name());
            row.createCell(3).setCellValue(mapping.getSheet2ColumnName());
            row.createCell(4).setCellValue(String.valueOf(mapping.isColumnNeedCompare()));
        }
    }

    private static List<ColumnMapping> readColumnMapping(Workbook wb) {
        Sheet sheet = wb.getSheet(COLUMN_MAPPING);
        List<ColumnMapping> mappings = new ArrayList<ColumnMapping>();
        for(Row row : sheet) {
            ColumnMapping mapping = new ColumnMapping();
            if(row.getRowNum() == 0) {
                Vector columnNames = new Vector();
                columnNames.add(row.getCell(0).getStringCellValue());
                columnNames.add(row.getCell(1).getStringCellValue());
                columnNames.add(row.getCell(2).getStringCellValue());
                columnNames.add(row.getCell(3).getStringCellValue());
                columnNames.add(row.getCell(4).getStringCellValue());
                config.setColumnMappingTableColumnNames(columnNames);
            } else {
                String sheet1Name = row.getCell(0).getStringCellValue();
                String sheet1ColumnName = row.getCell(1).getStringCellValue();
                String sheet2Name = row.getCell(2).getStringCellValue();
                String sheet2ColumnName = row.getCell(3).getStringCellValue();
                boolean isColumnNeedCompare = false;
                if("true".equalsIgnoreCase(row.getCell(4).getStringCellValue())) {
                    isColumnNeedCompare = true;
                }
                mapping.setSheet1Name(sheet1Name);
                mapping.setSheet1ColumnName(sheet1ColumnName);
                mapping.setSheet2Name(sheet2Name);
                mapping.setSheet2ColumnName(sheet2ColumnName);
                mapping.setColumnNeedCompare(isColumnNeedCompare);
                mappings.add(mapping);
            }
        }
        return mappings;
    }
    private static String getCellValue(Cell cell) {
        String value = "";
        if(cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double tmp = cell.getNumericCellValue();
                    value = new DecimalFormat("0").format(tmp);
                    break;
                case Cell.CELL_TYPE_BLANK:
                    value = "";
                    break;
                default:
                    System.err.println("Cell Type not correct :" + cell.getCellType());
            }
        }
        return value;
    }

    public static CompareConfig getConfig() {
        return config;
    }
}

