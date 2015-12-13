package com.ibm.spe.comparetool4.config;

import java.util.Vector;

/**
 * Created by luis on 2015/2/6.
 */
public class FFSheetMapping implements SheetMapping{
    private String financial1SheetName;
    private String financial2SheetName;
    private boolean isNeedCompare;

    public static Vector newRow() {
        Vector row = new Vector();
        row.add("");
        row.add("");
        row.add(false);
        return row;
    }

    public void setFinancial1SheetName(String financial1SheetName) {
        this.financial1SheetName = financial1SheetName;
    }

    public void setFinancial2SheetName(String financial2SheetName) {
        this.financial2SheetName = financial2SheetName;
    }

    public void setNeedCompare(boolean isNeedCompare) {
        this.isNeedCompare = isNeedCompare;
    }

    @Override
    public String getSheet1Name() {
        return financial1SheetName;
    }

    @Override
    public String getSheet2Name() {
        return financial2SheetName;
    }

    @Override
    public boolean isSheetNeedCompare() {
        return isNeedCompare;
    }

    @Override
    public String toString() {
        return String.format("FFSheetMapping[Column names:%s, %s, %s]\n",
                financial1SheetName, financial2SheetName, isNeedCompare);
    }
}
