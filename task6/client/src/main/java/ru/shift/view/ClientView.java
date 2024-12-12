package ru.shift.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.client.ClientData;
import ru.shift.message.implementation.PostNotification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ClientView extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(ClientView.class);

    private JScrollPane scrollPane;
    private JPanel textArea;
    private JTextField messageField;
    JButton sendButton;
    private final ClientData clientData;

    public ClientView(ClientData clientData) {
        super("Chat Client");
        this.clientData = clientData;
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMenu();
        createTextArea();
        createMessageField();

        setVisible(true);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu chatMenu = new JMenu("\u2699");
        menuBar.setBackground(new Color(243, 170, 197));

        JMenuItem chatMembers;
        chatMenu.add(chatMembers = new JMenuItem("Chat Members"));
        chatMembers.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "ChatUsers: " + clientData.getChatUsers());
        });
        chatMenu.addSeparator();

        menuBar.add(chatMenu);
        setJMenuBar(menuBar);
    }

    private void createTextArea() {
        textArea = new JPanel();
        textArea.setBackground(new Color(241, 207, 217));
        textArea.setLayout(new BoxLayout(textArea, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(textArea);
        add(scrollPane);
    }

    private void createMessageField() {
        messageField = new JTextField();
        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(243, 170, 197));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(messageField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);
        add(panel, BorderLayout.SOUTH);
    }

    public void addButtonListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }

    public String getTextMessage() {
        return messageField.getText();
    }

    public void showMessage(PostNotification message) {
        try {
            String messageText = message.getContent();
            String senderName = message.getUsername();
            String timestamp = String.valueOf(message.getTimestamp());

            var isMe = Objects.equals(message.getUsername(), clientData.getUsername());
            MessageView messageView = new MessageView(senderName, timestamp, messageText, isMe);
            textArea.add(messageView, BorderLayout.CENTER);
            textArea.revalidate();
            textArea.repaint();

            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());

            scrollPane.getViewport().setViewPosition(new Point(textArea.getWidth(), textArea.getHeight()));
        } catch (Throwable e) {
            logger.error("Error on showMessage", e);
        }
    }

    public void clearText() {
        messageField.setText("");
    }
}

