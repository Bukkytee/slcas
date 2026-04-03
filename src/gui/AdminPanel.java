package gui;

import controller.LibraryManager;
import model.Book;
import model.Journal;
import model.Magazine;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AdminPanel extends JPanel {
    private LibraryManager libraryManager;

    private JComboBox<String> itemsTypeCombo;
    private JTextField titleField, authorField, yearField;
    private JLabel dynamicLabel1, dynamicLabel2;
    private JTextField dynamicField1, dynamicField2;

    private JTextArea fullReportArea;

    public AdminPanel(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createEntryFormSection());
        add(Box.createRigidArea(new Dimension(0, 15)));

        add(createSystemControlsSection());
        add(Box.createRigidArea(new Dimension(0, 15)));

        add(createReportingSection());

    }

    private JPanel createEntryFormSection() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 5), "Add New Resource", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill =  GridBagConstraints.HORIZONTAL;

        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 0;
        panel.add(new JLabel("Item Type:"), gridBagConstraints);

        String[] types = {"Book", "Magazine", "Journal"};
        itemsTypeCombo = new JComboBox<>(types);
        gridBagConstraints.gridx = 1; gridBagConstraints.gridwidth = 3;
        panel.add(itemsTypeCombo, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;

        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 1;
        panel.add(new JLabel("Title:"), gridBagConstraints);
        titleField = new JTextField(15);
        gridBagConstraints.gridx = 1;
        panel.add(titleField, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        panel.add(new JLabel("Author:"), gridBagConstraints);
        authorField = new JTextField(15);
        gridBagConstraints.gridx = 3;
        panel.add(authorField, gridBagConstraints);

        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 2;
        panel.add(new JLabel("Year:"), gridBagConstraints);
        yearField = new JTextField(15);
        gridBagConstraints.gridx = 1;
        panel.add(yearField, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        dynamicLabel1 = new JLabel("ISBN:");
        panel.add(dynamicLabel1, gridBagConstraints);
        dynamicField1 = new JTextField(15);
        gridBagConstraints.gridx = 3;
        panel.add(dynamicField1, gridBagConstraints);

        gridBagConstraints.gridx = 2; gridBagConstraints.gridy = 3;
        dynamicLabel2 = new JLabel("Genre:");
        panel.add(dynamicLabel2, gridBagConstraints);
        dynamicField2 = new JTextField(15);
        gridBagConstraints.gridx = 3;
        panel.add(dynamicField2, gridBagConstraints);

        JButton addButton = new JButton("Add to Catalogue");
        addButton.setBackground(new Color(3, 105, 161));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 4; gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new Insets(15, 5, 5, 5);
        panel.add(addButton, gridBagConstraints);

        itemsTypeCombo.addActionListener(e -> updateDynamicFields());
        addButton.addActionListener(e -> processAddItem());
        return panel;
    }

    private JPanel createSystemControlsSection() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBorder(BorderFactory.createTitledBorder("System Controls"));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JButton undoButton = new JButton("Undo Last Action");
        undoButton.setBackground(new Color(220, 53, 69));
        undoButton.setForeground(Color.white);
        undoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton reminderButton = new JButton("Trigger Overdue Reminders");
        reminderButton.setBackground(new Color(255, 193, 7));
        reminderButton.setFont(new Font("Segoe UI", Font.BOLD, 12));

        panel.add(undoButton);
        panel.add(reminderButton);

        undoButton.addActionListener(e -> {
            libraryManager.handleUndoAction();
            JOptionPane.showMessageDialog(this, "Last action undone successfully.");
            refreshReports();
        });

        reminderButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Overdue reminders have been dispatched!");
        });

        return panel;
    }

    private JPanel createReportingSection() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Comprehensive System Report"));

        // Setup Monospaced text area to look like raw system output
        fullReportArea = new JTextArea();
        fullReportArea.setEditable(false);
        fullReportArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        fullReportArea.setBackground(new Color(248, 249, 250));

        // Wrap it in a scroll pane and add directly to the panel
        JScrollPane scrollPane = new JScrollPane(fullReportArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Load initial data
        refreshReports();

        return panel;
    }


    private void updateDynamicFields() {
        String selected = (String) itemsTypeCombo.getSelectedItem();

        if ("Book".equals(selected)) {
            dynamicLabel1.setText("ISBN:");
            dynamicLabel1.setVisible(true);
            dynamicField1.setVisible(true);

            dynamicLabel2.setText("Genre:");
            dynamicLabel2.setVisible(true);
            dynamicField2.setVisible(true);
        } else if ("Magazine".equals(selected)) {
            dynamicLabel1.setText("Issue No:");
            dynamicLabel1.setVisible(true);
            dynamicField1.setVisible(true);

            dynamicLabel2.setVisible(false);
            dynamicField2.setVisible(false);
        } else if ("Journal".equals(selected)) {
            dynamicLabel1.setText("Volume:");
            dynamicLabel1.setVisible(true);
            dynamicField1.setVisible(true);

            dynamicLabel2.setText("Field Of Study:");
            dynamicLabel2.setVisible(true);
            dynamicField2.setVisible(true);
        }

        revalidate();
        repaint();
    }

    private void processAddItem() {
        try {
            String type = (String) itemsTypeCombo.getSelectedItem();
            String title = titleField.getText();
            String author = authorField.getText();
            int year = Integer.parseInt(yearField.getText());

            String identifier = dynamicField1.getText();
            String category = dynamicField2.getText();

            libraryManager.handleAddItem(type, title, author, year, identifier, category);

            JOptionPane.showMessageDialog(this, type + " added successfully!");

            titleField.setText("");
            authorField.setText("");
            yearField.setText("");
            dynamicField1.setText("");
            dynamicField2.setText("");

            refreshReports();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric year!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshReports() {
        try {
            String reportText = libraryManager.generateReport();
            fullReportArea.setText(reportText);
            fullReportArea.setCaretPosition(0);
        } catch (Exception e) {
            fullReportArea.setText("====== SYSTEM ERROR ======\n\n" +
                    "Unable to generate report at this time.\n" +
                    "Details: " + e.getMessage());
        }
    }
}
