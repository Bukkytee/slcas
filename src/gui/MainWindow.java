package gui;

import controller.BorrowController;
import controller.LibraryManager;
import controller.SearchEngine;
import controller.SortingEngine;
import model.LibraryDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {
    private LibraryDatabase database;
    private LibraryManager libraryManager;
    private BorrowController borrowController;
    private SearchEngine searchEngine;
    private SortingEngine sortingEngine;

    private CardLayout cardLayout;
    private JPanel mainContentPanel;

    public MainWindow() {
        database = new LibraryDatabase();
        libraryManager = new LibraryManager(database);
        borrowController = new BorrowController(database);
        searchEngine = new SearchEngine(database);
        sortingEngine = new SortingEngine();

        libraryManager.loadSystemData();

        setTitle("Smart Library Circulation & Automation System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(12, 1, 0, 8));
        sidebar.setPreferredSize(new Dimension(170, 0));
        sidebar.setBackground(new Color(45, 52, 54));

        JLabel logoLabel = new JLabel("Library Menu", SwingConstants.CENTER);
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        sidebar.add(logoLabel);

        JButton viewItemsButton = createSidebarButton("View Items");
        JButton adminButton = createSidebarButton("Admin");
        JButton borrowReturnButton = createSidebarButton("Borrow & Return");
        JButton searchSortButton = createSidebarButton("Search & Sort");

        sidebar.add(viewItemsButton);
        sidebar.add(adminButton);
        sidebar.add(borrowReturnButton);
        sidebar.add(searchSortButton);

        add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);

        mainContentPanel.add(createPlaceholder("View Items Space"), "VIEW_ITEMS");
        mainContentPanel.add(createPlaceholder("Admin Space"), "ADMIN");
        mainContentPanel.add(createPlaceholder("Borrow & Return Space"), "BORROW_RETURN");
        mainContentPanel.add(createPlaceholder("Search & Sort Space"), "SEARCH_SORT");

        add(mainContentPanel, BorderLayout.CENTER);

        viewItemsButton.addActionListener(e -> {cardLayout.show(mainContentPanel, "VIEW_ITEMS");});
        adminButton.addActionListener(e -> {cardLayout.show(mainContentPanel, "ADMIN");});
        borrowReturnButton.addActionListener(e -> {cardLayout.show(mainContentPanel, "BORROW_RETURN");});
        searchSortButton.addActionListener(e -> {cardLayout.show(mainContentPanel, "SEARCH_SORT");});


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Application closing. Saving system data...");
                libraryManager.saveSystemData();
            }
        });
    }

    private JButton createSidebarButton(String text) {
            JButton button = new JButton(text);
            button.setFocusPainted(false);
            button.setFont(new Font("Segoe UI", Font.BOLD, 16));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(64, 73, 75));
            button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            return button;
    }

    private JPanel createPlaceholder(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.ITALIC, 28));
        label.setForeground(Color.LIGHT_GRAY);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow app = new MainWindow();
            app.setVisible(true);
        });
    }
}
