package com.ibm.spe.comparetool4;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.ibm.spe.comparetool4.gui.CompareTool;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				CompareTool mainFrame = new CompareTool();
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// set max size
//				mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
				mainFrame.setVisible(true);
			}
		});
	}

}
