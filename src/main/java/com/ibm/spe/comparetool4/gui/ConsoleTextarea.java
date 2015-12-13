package com.ibm.spe.comparetool4.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;

/**
 * Created by luis on 2015/2/4.
 */
public class ConsoleTextarea {
    private static Log logger = LogFactory.getLog(ConsoleTextarea.class);

    private static JTextArea logTxtArea = new JTextArea(20, 62);

    public static JTextArea getLogTxtArea() {
        return logTxtArea;
    }

    static {
        logTxtArea.setEditable(false);
    }

    public static void clear() {
        logTxtArea.setText("");
    }

    public static void info(String s) {
        logTxtArea.append(s + "\n");
        logger.info(s);
    }
}
