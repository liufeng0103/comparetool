package com.ibm.spe.comparetool4.gui.config;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.ibm.spe.comparetool4.config.ColumnMapping;
import com.ibm.spe.comparetool4.util.ConfigFileHelper;

/**
 * Created by luis on 2015/2/8.
 */
public class SheetColumnMappingConfig extends JPanel {

    private static final long serialVersionUID = -4825928582843045100L;

    private DefaultTableModel tableModel;

    public SheetColumnMappingConfig() {
        setLayout(new BorderLayout());

        // UI component
        tableModel = new CompareToolTableModel(ConfigFileHelper.getConfig().getColumnMappingTableData() , ConfigFileHelper.getConfig().getColumnMappingTableColumnNames());
        final JTable table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);

        JButton addBtn = new JButton("Add");
        JButton removeBtn = new JButton("Remove");

        // UI Layout
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1));
        buttonsPanel.add(addBtn);
        buttonsPanel.add(removeBtn);
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(buttonsPanel, BorderLayout.NORTH);

        add(new JScrollPane(table));
        add(rightPanel, BorderLayout.EAST);

        // Event
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(ColumnMapping.newRow());
            }
        });

        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() != -1) {
                    tableModel.removeRow(table.getSelectedRow());
                }
            }
        });
    }

    public void save() {
        ConfigFileHelper.getConfig().setColumnMappingTableData(tableModel.getDataVector());
    }
}
