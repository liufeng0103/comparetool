package com.ibm.spe.comparetool4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.ibm.spe.comparetool4.gui.config.CompareToolConfiguration;

public class CompareToolMenuBar extends JMenuBar {

    private static final long serialVersionUID = -6907010246344877225L;

    public CompareToolMenuBar() {
        // UI Component
        // Menus
        JMenu fileMenu = new JMenu("File");
        JMenu configurationMenu = new JMenu("Configuration");
        JMenu formatMenu = new JMenu("Format");
        JMenu viewMenu = new JMenu("View");
        JMenu helpMenu = new JMenu("Help");

        // Menu items
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open...");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save As...");
        JMenuItem pageSetupItem = new JMenuItem("Page Setup...");
        JMenuItem printItem = new JMenuItem("Print");
        JMenuItem closeItem = new JMenuItem("Close");

        JMenuItem compareConfigItem = new JMenuItem("Compare Config");

        // Keyboard shortcuts
        newItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));

        // Menu and item combination
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(pageSetupItem);
        fileMenu.add(printItem);
        fileMenu.addSeparator();
        fileMenu.add(closeItem);

        configurationMenu.add(compareConfigItem);

        // Add menus to the bar
        add(fileMenu);
        add(configurationMenu);
        add(formatMenu);
        add(viewMenu);
        add(helpMenu);

        // Event
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        closeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        final CompareToolConfiguration configuration = new CompareToolConfiguration();
        compareConfigItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configuration.setVisible(true);
            }
        });
    }
}
