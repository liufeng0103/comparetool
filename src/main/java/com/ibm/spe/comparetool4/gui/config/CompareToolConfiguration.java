package com.ibm.spe.comparetool4.gui.config;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.ibm.spe.comparetool4.util.ConfigFileHelper;
import com.ibm.spe.comparetool4.util.SwingUtil;


public class CompareToolConfiguration extends JFrame {
	
	private static final long serialVersionUID = 8423602647448755058L;

	private CommonConfig commonConfigPanel = new CommonConfig();
	private FFSheetMappingConfig ffSheetMappingConfigPanel =  new FFSheetMappingConfig();
	private FMSheetMappingConfig fmSheetMappingConfigPanel =  new FMSheetMappingConfig();
	private MMSheetMappingConfig mmSheetMappingConfigPanel =  new MMSheetMappingConfig();
	private SheetColumnMappingConfig sheetColumnMappingConfigPanel = new SheetColumnMappingConfig();
	
	public CompareToolConfiguration() {
		setTitle("Compare Tool Configuration");
		setSize(1000,700);
		SwingUtil.showOnScreenCenter(this);

		// UI Component
		final String commonConfig = "General";
		final String ffSheetMappingConfig = "FF Sheet Mapping";
		final String fmSheetMappingConfig = "FM Sheet Mapping";
		final String mmSheetMappingConfig = "MM Sheet Mapping";
		final String sheetColumnMappingConfig = "Sheet Column Mapping";
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(commonConfig);
		DefaultMutableTreeNode ffSheetsConfig = new DefaultMutableTreeNode(ffSheetMappingConfig);
		DefaultMutableTreeNode fmSheetsConfig = new DefaultMutableTreeNode(fmSheetMappingConfig);
		DefaultMutableTreeNode mmSheetsConfig = new DefaultMutableTreeNode(mmSheetMappingConfig);
		DefaultMutableTreeNode sheetColumnsConfig = new DefaultMutableTreeNode(sheetColumnMappingConfig);
		final JTree tree = new JTree(root);

		JButton saveBtn = new JButton("Save");
		JButton okBtn = new JButton("Ok");
		JButton cancelBtn = new JButton("Cancel");

		// UI layout
		root.add(ffSheetsConfig);
		root.add(fmSheetsConfig);
		root.add(mmSheetsConfig);
		root.add(sheetColumnsConfig);

		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		final JPanel rightJPanel = new JPanel();
		rightJPanel.setLayout(new BorderLayout());
		jSplitPane.setLeftComponent(tree);
		jSplitPane.setRightComponent(rightJPanel);
		jSplitPane.setDividerLocation(200);
		jSplitPane.setDividerSize(5);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(saveBtn);
		buttonsPanel.add(okBtn);
		buttonsPanel.add(cancelBtn);
		JPanel buttonRightLayoutPanel = new JPanel();
		buttonRightLayoutPanel.setLayout(new BorderLayout());
		buttonRightLayoutPanel.add(buttonsPanel, BorderLayout.EAST);

		add(jSplitPane);
		add(buttonRightLayoutPanel, BorderLayout.SOUTH);

		SwingUtil.updateUILookAndFeel(CompareToolConfiguration.this);

		// Event

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				if(node == null) return;
				String selectedNode = (String)node.getUserObject();
				if(commonConfig.equals(selectedNode)) {
					rightJPanel.removeAll();
					rightJPanel.add(commonConfigPanel);
					SwingUtil.updateUILookAndFeel(rightJPanel);
				} else if(ffSheetMappingConfig.equals(selectedNode)) {
					rightJPanel.removeAll();
					rightJPanel.add(ffSheetMappingConfigPanel);
					SwingUtil.updateUILookAndFeel(rightJPanel);
				} else if(fmSheetMappingConfig.equals(selectedNode)) {
					rightJPanel.removeAll();
					rightJPanel.add(fmSheetMappingConfigPanel);
					SwingUtil.updateUILookAndFeel(rightJPanel);
				} else if(mmSheetMappingConfig.equals(selectedNode)) {
					rightJPanel.removeAll();
					rightJPanel.add(mmSheetMappingConfigPanel);
					SwingUtil.updateUILookAndFeel(rightJPanel);
				} else if(sheetColumnMappingConfig.equals(selectedNode)) {
					rightJPanel.removeAll();
					rightJPanel.add(sheetColumnMappingConfigPanel);
					SwingUtil.updateUILookAndFeel(rightJPanel);
				}
			}
		});

		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
				setVisible(false);
			}
		});

		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

	private void save() {
		commonConfigPanel.save();
		ffSheetMappingConfigPanel.save();
		fmSheetMappingConfigPanel.save();
		mmSheetMappingConfigPanel.save();
		sheetColumnMappingConfigPanel.save();
		ConfigFileHelper.saveConfig();
	}
}
