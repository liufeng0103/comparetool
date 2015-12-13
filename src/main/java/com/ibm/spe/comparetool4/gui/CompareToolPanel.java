package com.ibm.spe.comparetool4.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.ibm.spe.comparetool4.compare.CompareTest;
import com.ibm.spe.comparetool4.util.ConfigFileHelper;

public class CompareToolPanel extends JPanel {

    private static final long serialVersionUID = 7243772609054330076L;

    public CompareToolPanel() {
        // UI component
        final JComboBox compareTypeCombo = new JComboBox(CompareTest.compareTypes);
        final JTextField file1Txt = new JTextField(15);
        final JTextField file2Txt = new JTextField(15);
        final JButton runBtn = new JButton("Start");

        // UI layout
        JPanel comparePanel = new JPanel();
        comparePanel.add(compareTypeCombo);
        comparePanel.add(new JLabel("From File:"));
        comparePanel.add(file1Txt);
        comparePanel.add(new JLabel("To File:  "));
        comparePanel.add(file2Txt);
        comparePanel.add(runBtn);

        JPanel compareLayoutPanel = new JPanel();
        compareLayoutPanel.setLayout(new BorderLayout());
        compareLayoutPanel.add(comparePanel, BorderLayout.WEST); // left align

        setLayout(new BorderLayout());
        add(compareLayoutPanel, BorderLayout.NORTH);
        add(new JScrollPane(ConsoleTextarea.getLogTxtArea()));

        // Event
        file1Txt.addMouseListener(new FileChooseEvent());
        file2Txt.addMouseListener(new FileChooseEvent());
        runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!"".equals(file1Txt.getText()) && !"".equals(file2Txt.getText())) {
                    runBtn.setEnabled(false);
                    ConsoleTextarea.clear();

                    new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() {
                            ConsoleTextarea.info("---Start comparing---");
                            long start = System.currentTimeMillis();
                            try {
                                new CompareTest().executeCompare(file1Txt.getText(), file2Txt.getText(), compareTypeCombo.getSelectedItem().toString());
                            } catch (Exception exception) {
                                ConsoleTextarea.info("Error:" + exception.getMessage());
                                exception.printStackTrace();
                            }
                            ConsoleTextarea.info("---Done total time: " + (System.currentTimeMillis() - start) + "ms---");
                            return null;
                        }

                        @Override
                        protected void done() {
                            runBtn.setEnabled(true);
                        }

                    }.execute();
                }
            }
        });
    }
}

class FileChooseEvent extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        JFileChooser fileChoose = new JFileChooser();
        String desktop = ConfigFileHelper.getConfig().getFileChooseDefaultPath();
        fileChoose.setCurrentDirectory(new File(desktop));
        fileChoose.setDialogTitle("Please select the file");
        fileChoose.setFileFilter(new FileNameExtensionFilter("Excel(xls,xlsx)", "xls", "xlsx"));
        if (JFileChooser.APPROVE_OPTION == fileChoose.showOpenDialog(null)) {
            ((JTextField) e.getSource()).setText(fileChoose.getSelectedFile().getAbsolutePath());
        }
    }
}

