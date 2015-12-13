package com.ibm.spe.comparetool4.gui;

import javax.swing.JLabel;
import javax.swing.JToolBar;

public class CompareToolStatusBar extends JToolBar {

	private static final long serialVersionUID = 1651950376072267029L;

	public CompareToolStatusBar() {
		JLabel label = new JLabel("This is a status bar");
		label.setToolTipText("This is a tool tip text");
		add(label);
		addSeparator();
		
		// Can't move status bar
		setFloatable(false);
	}
}
