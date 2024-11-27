package ru.shift.view;

import javax.swing.*;
import java.awt.*;

public class HighScoresWindow extends JDialog {
    public static final String DEFAULT_RECORD_TEXT = "Unknown - 999";

    private final JTextArea noviceRecordLabel;
    private final JTextArea mediumRecordLabel;
    private final JTextArea expertRecordLabel;

    public HighScoresWindow(JFrame owner) {
        super(owner, "High Scores", true);

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        int gridY = 0;

        contentPane.add(createJTextArea("Novice:", layout, gridY++, 0));
        noviceRecordLabel = createJTextArea(DEFAULT_RECORD_TEXT, layout, gridY++, 10);
        JScrollPane noviceScrollPane = createScrollPanel(noviceRecordLabel, layout, gridY++, 0);
        noviceScrollPane.setPreferredSize(new Dimension(150, 60));
        contentPane.add(noviceScrollPane);

        contentPane.add(createJTextArea("Medium:", layout, gridY++, 10));
        mediumRecordLabel = createJTextArea(DEFAULT_RECORD_TEXT, layout, gridY++, 10);
        JScrollPane mediumScrollPane = createScrollPanel(mediumRecordLabel, layout, gridY++, 0);
        mediumScrollPane.setPreferredSize(new Dimension(150, 60));
        contentPane.add(mediumScrollPane);


        contentPane.add(createJTextArea("Expert:", layout, gridY++, 10));
        expertRecordLabel = createJTextArea(DEFAULT_RECORD_TEXT, layout, gridY++, 10);
        JScrollPane expertScrollPane = createScrollPanel(expertRecordLabel, layout, gridY++, 0);
        expertScrollPane.setPreferredSize(new Dimension(150, 60));
        contentPane.add(expertScrollPane);

        contentPane.add(createCloseButton(layout, gridY++));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(200, 350));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public void setNoviceRecord(String string) {
        noviceRecordLabel.setText(string);
    }

    public void setMediumRecord(String string) {
        mediumRecordLabel.setText(string);
    }

    public void setExpertRecord(String string) {
        expertRecordLabel.setText(string);
    }

    private GridBagConstraints createGridBagConstraints(int gridY, int margin) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(margin, 0, 0, 0);
        return gbc;
    }

    private JTextArea createJTextArea(String labelText, GridBagLayout layout, int gridY, int margin) {
        JTextArea label = new JTextArea(labelText);
        layout.setConstraints(label, createGridBagConstraints(gridY, margin));

        return label;
    }

    private JScrollPane createScrollPanel(JTextArea labelText, GridBagLayout layout, int gridY, int margin) {
        JScrollPane label = new JScrollPane(labelText);
        layout.setConstraints(label, createGridBagConstraints(gridY, margin));

        return label;
    }

    private JButton createCloseButton(GridBagLayout layout, int gridY) {
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        okButton.setPreferredSize(new Dimension(60, 25));
        layout.setConstraints(okButton, createGridBagConstraints(gridY, 10));

        return okButton;
    }
}
