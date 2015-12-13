package com.ibm.spe.comparetool4.gui.config;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * Created by luis on 2015/2/8.
 */
public class CompareToolTableModel extends DefaultTableModel {

    public CompareToolTableModel(Vector data, Vector columnNames) {
        super(data, columnNames);
    }

    @Override
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
