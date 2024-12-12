package ru.shift.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EnterNameWindow {
    JDialog dialog;
    JTextField textField;
    JButton submitButton;

    public EnterNameWindow() {
        dialog = new JDialog();
        dialog.setTitle("Input");
        dialog.setModal(true);
        dialog.setSize(300, 150);
        dialog.setLayout(new FlowLayout());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Enter your name");
        dialog.add(label);

        textField = new JTextField(20);
        dialog.add(textField);

        submitButton = new JButton("OK");
        dialog.add(submitButton);
    }

    public void addButtonListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public String getEnterName() {
        return textField.getText();
    }

    public void open() {
        dialog.setVisible(true);
    }

    public void close() {
        dialog.dispose();
    }
}