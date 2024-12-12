package ru.shift.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EnterHostAndPortWindow {
    JDialog dialog;
    JTextField textFieldForHost;
    JTextField textFieldForPort;
    JButton submitButton;

    public EnterHostAndPortWindow() {
        dialog = new JDialog();
        dialog.setTitle("Input");
        dialog.setModal(true);
        dialog.setSize(300, 150);
        dialog.setLayout(new FlowLayout());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Enter host:");
        dialog.add(label);
        textFieldForHost = new JTextField(20);
        dialog.add(textFieldForHost);


        JLabel labelPort = new JLabel("Enter port:");
        dialog.add(labelPort);
        textFieldForPort = new JTextField(20);
        dialog.add(textFieldForPort);


        submitButton = new JButton("OK");
        dialog.add(submitButton);
    }

    public void addButtonListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public String getHost() {
        return textFieldForHost.getText();
    }

    public String getPort() {
        return textFieldForPort.getText();
    }

    public void open() {
        dialog.setVisible(true);
    }

    public void close() {
        dialog.dispose();
    }
}