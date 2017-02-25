package com.ibm.spe.comparetool4.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by luis on 2015/2/6.
 */
public class CompareConfig {
    private List<CountryMapping> countryMappings;
    private Vector fmSheetMappingTableColumnNames;
    private List<SheetMapping> fmSheetMappings;
    private Vector ffSheetMappingTableColumnNames;
    private List<SheetMapping> ffSheetMappings;
    private Vector mmSheetMappingTableColumnNames;
    private List<SheetMapping> mmSheetMappings;
    private Vector columnMappingTableColumnNames;
    private List<ColumnMapping> columnMappings;
    private Map<String, String> commonConfigs;
    private Map<String, String> soItsMappings;

    public Map<String, String> getSoItsMappings() {
		return soItsMappings;
	}

	public void setSoItsMappings(Map<String, String> soItsMappings) {
		this.soItsMappings = soItsMappings;
	}

	public void setCountryMappings(List<CountryMapping> countryMappings) {
        this.countryMappings = countryMappings;
    }

    public List<SheetMapping> getFmSheetMappings() {
        return fmSheetMappings;
    }

    public void setFmSheetMappings(List<SheetMapping> fmSheetMappings) {
        this.fmSheetMappings = fmSheetMappings;
    }

    public List<SheetMapping> getFfSheetMappings() {
        return ffSheetMappings;
    }

    public void setFfSheetMappings(List<SheetMapping> ffSheetMappings) {
        this.ffSheetMappings = ffSheetMappings;
    }

    public List<SheetMapping> getMmSheetMappings() {
        return mmSheetMappings;
    }

    public void setMmSheetMappings(List<SheetMapping> mmSheetMappings) {
        this.mmSheetMappings = mmSheetMappings;
    }

    public void setColumnMappings(List<ColumnMapping> columnMappings) {
        this.columnMappings = columnMappings;
    }

    public List<ColumnMapping> getColumnMappings() {
        return columnMappings;
    }

    public void setCommonConfigs(Map<String, String> commonConfigs) {
        this.commonConfigs = commonConfigs;
    }

    public Vector getFmSheetMappingTableColumnNames() {
        return fmSheetMappingTableColumnNames;
    }

    public void setFmSheetMappingTableColumnNames(Vector fmSheetMappingTableColumnNames) {
        this.fmSheetMappingTableColumnNames = fmSheetMappingTableColumnNames;
    }

    public Vector getFfSheetMappingTableColumnNames() {
        return ffSheetMappingTableColumnNames;
    }

    public void setFfSheetMappingTableColumnNames(Vector ffSheetMappingTableColumnNames) {
        this.ffSheetMappingTableColumnNames = ffSheetMappingTableColumnNames;
    }

    public Vector getMmSheetMappingTableColumnNames() {
        return mmSheetMappingTableColumnNames;
    }

    public void setMmSheetMappingTableColumnNames(Vector mmSheetMappingTableColumnNames) {
        this.mmSheetMappingTableColumnNames = mmSheetMappingTableColumnNames;
    }

    public Vector getColumnMappingTableColumnNames() {
        return columnMappingTableColumnNames;
    }

    public void setColumnMappingTableColumnNames(Vector columnMappingTableColumnNames) {
        this.columnMappingTableColumnNames = columnMappingTableColumnNames;
    }

    public Vector getColumnMappingTableData() {
        Vector data = new Vector();
        for(ColumnMapping columnMapping : columnMappings) {
            Vector row = new Vector();
            row.add(columnMapping.getSheet1Name());
            row.add(columnMapping.getSheet1ColumnName());
            row.add(columnMapping.getSheet2Name());
            row.add(columnMapping.getSheet2ColumnName());
            row.add(columnMapping.isColumnNeedCompare());
            data.add(row);
        }
        return data;
    }

    public void setColumnMappingTableData(Vector<Vector> data) {
        List<ColumnMapping> columnMappings = new ArrayList<ColumnMapping>();
        for(Vector row : data) {
            ColumnMapping mapping = new ColumnMapping();
            mapping.setSheet1Name((String) row.get(0));
            mapping.setSheet1ColumnName((String) row.get(1));
            mapping.setSheet2Name((String) row.get(2));
            mapping.setSheet2ColumnName((String) row.get(3));
            mapping.setColumnNeedCompare((Boolean) row.get(4));
            columnMappings.add(mapping);
        }
        this.columnMappings = columnMappings;
    }

    public Vector getFFSheetMappingTableData() {
        Vector data = new Vector();
        for(SheetMapping ffSheetMapping : ffSheetMappings) {
            Vector row = new Vector();
            row.add(ffSheetMapping.getSheet1Name());
            row.add(ffSheetMapping.getSheet2Name());
            row.add(ffSheetMapping.isSheetNeedCompare());
            data.add(row);
        }
        return data;
    }

    public void setFFSheetMappingTableData(Vector<Vector> data) {
        List<SheetMapping> ffSheetMappings = new ArrayList<SheetMapping>();
        for(Vector row : data) {
            FFSheetMapping mapping = new FFSheetMapping();
            mapping.setFinancial1SheetName((String)row.get(0));
            mapping.setFinancial2SheetName((String)row.get(1));
            mapping.setNeedCompare((Boolean)row.get(2));
            ffSheetMappings.add(mapping);
        }
        this.ffSheetMappings = ffSheetMappings;
    }

    public Vector getFMSheetMappingTableData() {
        Vector data = new Vector();
        for(SheetMapping fmSheetMapping : fmSheetMappings) {
            Vector row = new Vector();
            row.add(fmSheetMapping.getSheet1Name());
            row.add(fmSheetMapping.getSheet2Name());
            row.add(fmSheetMapping.isSheetNeedCompare());
            data.add(row);
        }
        return data;
    }

    public void setFMSheetMappingTableData(Vector<Vector> data) {
        List<SheetMapping> fmSheetMappings = new ArrayList<SheetMapping>();
        for(Vector row : data) {
            FMSheetMapping mapping = new FMSheetMapping();
            mapping.setFinancialSheetName((String) row.get(0));
            mapping.setMrtSheetName((String) row.get(1));
            mapping.setNeedCompare((Boolean)row.get(2));
            fmSheetMappings.add(mapping);
        }
        this.fmSheetMappings = fmSheetMappings;
    }

    public Vector getMMSheetMappingTableData() {
        Vector data = new Vector();
        for(SheetMapping mmSheetMapping : mmSheetMappings) {
            Vector row = new Vector();
            row.add(mmSheetMapping.getSheet1Name());
            row.add(mmSheetMapping.getSheet2Name());
            row.add(mmSheetMapping.isSheetNeedCompare());
            data.add(row);
        }
        return data;
    }

    public void setMMSheetMappingTableData(Vector<Vector> data) {
        List<SheetMapping> mmSheetMappings = new ArrayList<SheetMapping>();
        for(Vector row : data) {
            MMSheetMapping mapping = new MMSheetMapping();
            mapping.setMrt1SheetName((String) row.get(0));
            mapping.setMrt2SheetName((String) row.get(1));
            mapping.setNeedCompare((Boolean)row.get(2));
            mmSheetMappings.add(mapping);
        }
        this.mmSheetMappings = mmSheetMappings;
    }

    public List<ColumnMapping> getNeedComparedColumnForSheet(String sheetName, String compareType) {
        List<ColumnMapping> columns = new ArrayList<ColumnMapping>();
        for(ColumnMapping column : columnMappings) {
            if(sheetName.equals(column.getSheet1Name()) && column.isColumnNeedCompare()) {
                columns.add(column);
            }
        }
        return columns;
    }

    public String getCountryCode(String country) {
        for(CountryMapping countryMapping : countryMappings) {
            if(countryMapping.getCountry().equals(country)) {
                return countryMapping.getCountryCode();
            }
        }
        // exception
        return null;
    }

    public String getFileChooseDefaultPath() {
        String path = commonConfigs.get("file_choose_default_path");
        if(new File(path).exists()) {
            return path;
        } else {
            return System.getProperty("user.home") + "\\Desktop";
        }
    }

    public void setFileChooseDefaultPath(String filePath) {
        commonConfigs.put("file_choose_default_path", filePath);
    }

    public String getResultFileName() {
        String fileName = commonConfigs.get("result_file_name");
        if("".equals(fileName)) {
            return "CompareResult.xls";
        }
        return fileName;
    }

    public void setResultFileName(String resultFileName) {
        commonConfigs.put("result_file_name", resultFileName);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", countryMappings, fmSheetMappings, ffSheetMappings, mmSheetMappings, columnMappings);
    }
}
