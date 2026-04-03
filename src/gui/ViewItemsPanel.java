package gui;

import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewItemsPanel extends JPanel {
    private LibraryDatabase database;

    private JLabel totalCountLabel;
    private JLabel booksCountLabel;
    private JLabel magazinesCountLabel;
    private JLabel journalsCountLabel;
    private DefaultTableModel tableModel;
    private JTable itemsTable;

    public ViewItemsPanel(LibraryDatabase database) {
        this.database = database;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createTopbar(), BorderLayout.NORTH);

        add(createMainTableArea(), BorderLayout.CENTER);

        refreshData();
    }

    private JPanel createTopbar() {
        JPanel topBar = new JPanel(new GridLayout(1, 4, 10, 0));
        topBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        topBar.setBackground(new Color(245, 245, 245));

        Font widgetFont = new Font("Segoe UI", Font.BOLD, 16);

        totalCountLabel = new JLabel("Total Resources: 0");
        totalCountLabel.setFont(widgetFont);

        booksCountLabel = new JLabel("Books: 0");
        booksCountLabel.setFont(widgetFont);
        booksCountLabel.setForeground(new Color(0, 102, 204));

        magazinesCountLabel = new JLabel("Magazines: 0");
        magazinesCountLabel.setFont(widgetFont);
        magazinesCountLabel.setForeground(new Color(0, 153, 76));

        journalsCountLabel = new JLabel("Journals: 0");
        journalsCountLabel.setFont(widgetFont);
        journalsCountLabel.setForeground(new Color(204, 102, 0));

        topBar.add(totalCountLabel);
        topBar.add(booksCountLabel);
        topBar.add(magazinesCountLabel);
        topBar.add(journalsCountLabel);

        return topBar;
    }

    private JPanel createMainTableArea() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        String[] columns = {"ID", "Title", "Author", "Year", "Category", "Availability Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        itemsTable = new JTable(tableModel);
        itemsTable.setRowHeight(30);
        itemsTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        itemsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        itemsTable.getTableHeader().setBackground(new Color(230, 230, 230));
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        itemsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        component.setBackground(Color.WHITE);
                    } else {
                        component.setBackground(new Color(245, 248, 250));
                    }
                }
                return component;
            }
        });

        JScrollPane scrollPane = new JScrollPane(itemsTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    public void refreshData() {
        tableModel.setRowCount(0);

        int books = 0;
        int magazines = 0;
        int journals = 0;

        try {
            if (database == null || database.getCatalogue() == null) {
                System.out.println("No items to display.");

                totalCountLabel.setText("Total Resources: 0");
                booksCountLabel.setText("Books: 0");
                magazinesCountLabel.setText("Magazines: 0");
                journalsCountLabel.setText("Journals: 0");

                return;
            }

            for (LibraryItem item : database.getCatalogue()) {
                String category = "Unknown";
                String author = "N/A";
                String year = "N/A";

                if (item instanceof Book) {
                    category = "Book";
                    books++;
                    author = item.getAuthor();
                    year = String.valueOf(item.getYear());
                } else if (item instanceof Magazine) {
                    category = "Magazine";
                    magazines++;
                    author = item.getAuthor();
                    year = String.valueOf(item.getYear());
                } else if (item instanceof Journal) {
                    category = "Journal";
                    journals++;
                    author = item.getAuthor();
                    year = String.valueOf(item.getYear());
                }

                String status = item.isAvailable() ? "Available" : "Checked Out";

                tableModel.addRow(new Object[] {
                        item.getId(),
                        item.getTitle(),
                        author,
                        year,
                        category,
                        status
                });

                int totalResources = books + magazines + journals;
                totalCountLabel.setText("Total Resources: " + totalResources);
                booksCountLabel.setText("Books: " + books);
                magazinesCountLabel.setText("Magazines: " + magazines);
                journalsCountLabel.setText("Journals: " + journals);
            }
        } catch (Exception e) {
            System.err.println("Error while refreshing data: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred while loading items.", "Data Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
