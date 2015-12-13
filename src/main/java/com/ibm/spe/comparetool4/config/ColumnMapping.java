package com.ibm.spe.comparetool4.config;

import java.util.Vector;

/**
 * Created by luis on 2015/2/6.
 */
public class ColumnMapping {
    private String sheet1Name;
    private String sheet1ColumnName;
    private String sheet2Name;
    private String sheet2ColumnName;
    private boolean isColumnNeedCompare;

    public static Vector newRow() {
        Vector row = new Vector();
        row.add("");
        row.add("");
        row.add("");
        row.add("");
        row.add(false);
        return row;
    }

    public void setSheet1Name(String sheet1Name) {
        this.sheet1Name = sheet1Name;
    }

    public void setSheet1ColumnName(String sheet1ColumnName) {
        this.sheet1ColumnName = sheet1ColumnName;
    }

    public void setSheet2Name(String sheet2Name) {
        this.sheet2Name = sheet2Name;
    }

    public void setSheet2ColumnName(String sheet2ColumnName) {
        this.sheet2ColumnName = sheet2ColumnName;
    }

    public String getSheet1ColumnName() {
        return sheet1ColumnName;
    }

    public String getSheet2Name() {
        return sheet2Name;
    }

    public String getSheet2ColumnName() {
        return sheet2ColumnName;
    }

    public boolean isColumnNeedCompare() {
        return isColumnNeedCompare;
    }

    public void setColumnNeedCompare(boolean isColumnNeedCompare) {
        this.isColumnNeedCompare = isColumnNeedCompare;
    }

    public String getSheet1Name() {
        return sheet1Name;
    }

    @Override
    public String toString() {
        return String.format("ColumnMapping[%s, %s, %s, %s, %s]\n",
                sheet1Name, sheet1ColumnName, sheet2Name, sheet2ColumnName, isColumnNeedCompare);
    }
}
