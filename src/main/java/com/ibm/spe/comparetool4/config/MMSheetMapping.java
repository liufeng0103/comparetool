package com.ibm.spe.comparetool4.config;

import java.util.Vector;

/**
 * Created by luis on 2015/2/6.
 */
public class MMSheetMapping implements SheetMapping {
    private String mrt1SheetName;
    private String mrt2SheetName;
    private boolean isNeedCompare;

    public static Vector newRow() {
        Vector row = new Vector();
        row.add("");
        row.add("");
        row.add(false);
        return row;
    }

    public void setMrt1SheetName(String mrt1SheetName) {
        this.mrt1SheetName = mrt1SheetName;
    }

    public void setMrt2SheetName(String mrt2SheetName) {
        this.mrt2SheetName = mrt2SheetName;
    }

    public void setNeedCompare(boolean isNeedCompare) {
        this.isNeedCompare = isNeedCompare;
    }

    @Override
    public String getSheet1Name() {
        return mrt1SheetName;
    }

    @Override
    public String getSheet2Name() {
        return mrt2SheetName;
    }

    @Override
    public boolean isSheetNeedCompare() {
        return isNeedCompare;
    }

    @Override
    public String toString() {
        return String.format("MMSheetMapping[Column names:%s, %s, %s]\n",
                mrt1SheetName, mrt2SheetName, isNeedCompare);
    }
}
