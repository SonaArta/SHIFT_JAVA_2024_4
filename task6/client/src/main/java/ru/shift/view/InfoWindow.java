package ru.shift.view;

import javax.swing.*;

public class InfoWindow {
    public void showInfoWindow(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorWindow(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.ERROR_MESSAGE);
    }
}
