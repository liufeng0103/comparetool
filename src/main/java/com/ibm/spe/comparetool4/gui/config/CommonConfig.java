package com.ibm.spe.comparetool4.gui.config;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ibm.spe.comparetool4.util.ConfigFileHelper;

/**
 * Created by luis on 2015/2/8.
 */
public class CommonConfig extends JPanel {
    private JTextField filePathTxt;
    private JTextField resultFileNameTxt;

    public CommonConfig() {
        setLayout(new BorderLayout());

        // UI component
        JLabel filePathLabel       = new JLabel("File choose path ");
        JLabel resultFileNameLabel = new JLabel("Result file name ");
        filePathTxt = new JTextField();
        filePathTxt.setEditable(false);
        filePathTxt.setText(ConfigFileHelper.getConfig().getFileChooseDefaultPath());
        resultFileNameTxt = new JTextField();
        resultFileNameTxt.setText(ConfigFileHelper.getConfig().getResultFileName());

        // UI Layout
        JPanel filePathPanel = new JPanel(new BorderLayout());
        filePathPanel.add(filePathLabel, BorderLayout.WEST);
        filePathPanel.add(filePathTxt);

        JPanel resultFileNamePanel = new JPanel(new BorderLayout());
        resultFileNamePanel.add(resultFileNameLabel, BorderLayout.WEST);
        resultFileNamePanel.add(resultFileNameTxt);

        JPanel boxLayoutPanel = new JPanel();
        boxLayoutPanel.setLayout(new BoxLayout(boxLayoutPanel, BoxLayout.Y_AXIS));
        boxLayoutPanel.add(filePathPanel);
        boxLayoutPanel.add(resultFileNamePanel);

        add(boxLayoutPanel, BorderLayout.NORTH);

        // Event
        filePathTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChoose = new JFileChooser();
                fileChoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChoose.setCurrentDirectory(new File(((JTextField) e.getSource()).getText()));
                fileChoose.setDialogTitle("Please select the directory");
                if (JFileChooser.APPROVE_OPTION == fileChoose.showOpenDialog(null)) {
                    ((JTextField) e.getSource()).setText(fileChoose.getSelectedFile().getAbsolutePath());
                }
            }
        });
    }

    public void save() {
        ConfigFileHelper.getConfig().setFileChooseDefaultPath(filePathTxt.getText());
        ConfigFileHelper.getConfig().setResultFileName(resultFileNameTxt.getText());
    }
}
