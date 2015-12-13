package com.ibm.spe.comparetool4.config;

import java.util.Vector;

/**
 * Created by luis on 2015/2/6.
 */
public class FMSheetMapping implements SheetMapping {
    private String financialSheetName;
    private String mrtSheetName;
    private boolean isNeedCompare;

    public static Vector newRow() {
        Vector row = new Vector();
        row.add("");
        row.add("");
        row.add(false);
        return row;
    }

    public void setFinancialSheetName(String financialSheetName) {
        this.financialSheetName = financialSheetName;
    }

    public void setMrtSheetName(String mrtSheetName) {
        this.mrtSheetName = mrtSheetName;
    }

    public void setNeedCompare(boolean isNeedCompare) {
        this.isNeedCompare = isNeedCompare;
    }

    @Override
    public String getSheet1Name() {
        return financialSheetName;
    }

    @Override
    public String getSheet2Name() {
        return mrtSheetName;
    }

    @Override
    public boolean isSheetNeedCompare() {
        return isNeedCompare;
    }

    @Override
    public String toString() {
        return String.format("FMSheetMapping[Column names:%s, %s, %s]\n",
                financialSheetName, mrtSheetName, isNeedCompare);
    }
}
