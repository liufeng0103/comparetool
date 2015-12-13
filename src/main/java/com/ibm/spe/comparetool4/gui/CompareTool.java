package com.ibm.spe.comparetool4.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.ibm.spe.comparetool4.util.SwingUtil;


public class CompareTool extends JFrame {
	
	private static final long serialVersionUID = -9112199219018036357L;

	public CompareTool() {
		setTitle("SPE Compare Tool");

		// Menu bar
		setJMenuBar(new CompareToolMenuBar());

		// Center content
		add(new CompareToolPanel());

		// Status bar
		add(new CompareToolStatusBar(), BorderLayout.SOUTH);
		
		pack();
		SwingUtil.showOnScreenCenter(CompareTool.this);
		SwingUtil.updateUILookAndFeel(CompareTool.this);
	}
}
