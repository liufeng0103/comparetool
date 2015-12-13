package com.ibm.spe.comparetool4.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.ibm.spe.comparetool4.util.SwingUtil;

/**
 * Created by luis on 2015/2/10.
 */
public class MessageDisplay {

    private static JFrame frame = new JFrame();
    private static JTextArea messageTxtField = new JTextArea(20, 40);

    static {
        JButton okBtn = new JButton("Ok");
        messageTxtField.setEditable(false);
        frame.setTitle("Message");
        frame.add(new JScrollPane(messageTxtField));
        frame.add(okBtn, BorderLayout.SOUTH);
        frame.pack();
        SwingUtil.showOnScreenCenter(frame);
        SwingUtil.updateUILookAndFeel(frame);
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
    }

    public static void show(String msg) {
        messageTxtField.setText("");
        messageTxtField.setText(msg);
        frame.setVisible(true);
    }

}
