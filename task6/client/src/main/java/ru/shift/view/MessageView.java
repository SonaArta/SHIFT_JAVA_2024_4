package ru.shift.view;

import javax.swing.*;
import java.awt.*;

public class MessageView extends JPanel {
    private final String senderName;
    private final String timestamp;
    private final String messageText;

    public MessageView(String senderName, String timestamp, String messageText, boolean isMe) {
        this.senderName = senderName;
        this.timestamp = timestamp;
        this.messageText = messageText;

        if (isMe) {
            setBackground(new Color(175, 124, 181));
        } else {
            setBackground(new Color(223, 156, 176));
        }

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setMaximumSize(new Dimension(300, getPreferredSize().height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        graphics.setColor(Color.BLACK);
        graphics.drawRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 100);
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        Graphics2D graphics = (Graphics2D) g;

        graphics.drawString(senderName, 10, 20);

        FontMetrics metrics = graphics.getFontMetrics();
        int width = metrics.stringWidth(timestamp);
        graphics.drawString(timestamp, getWidth() - width - 10, getHeight() - 10);

        graphics.drawString(messageText, 10, 40);
    }
}

