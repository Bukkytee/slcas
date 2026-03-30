package controller;

import model.*;
import utils.FileHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Handles adding/removing items, generating reports and undo items.
public class LibraryManager {
    private final LibraryDatabase database;

    public LibraryManager(LibraryDatabase database) {
        this.database = database;
    }

   public void handleAddItem(String itemType, String title, String author, int year, String identifier, String category) {
        LibraryItem newItem;

        switch (itemType.toUpperCase()) {
            case "BOOK":
                newItem = new Book(null, title, author, year, true, identifier, category);
                break;
            case "MAGAZINE":
                int issueNumber = Integer.parseInt(identifier);
                newItem = new Magazine(null, title, author, year, true, issueNumber);
                break;
            case "JOURNAL":
                newItem = new Journal(null, title, author, year, true, identifier, category);
                break;
            default:
                    System.out.println("Invalid item type");
                    return;
        }

        database.addItem(newItem);
        System.out.println("Item added successfully");
    }

    public boolean handleRemoveItem(String itemId) {
        for (LibraryItem item : database.getCatalogue()) {
            if (item.getId().equalsIgnoreCase(itemId)) {
                database.removeItem(item);
                System.out.println("Item removed successfully");
                return true;
            }
        }
        System.out.println("Item not found");
        return false;
    }

    public String generateReport(){
        StringBuilder report = new StringBuilder();
        report.append("====== SYSTEM ACTIVITY REPORT ======\n\n");

        report.append("1. MOST BORROWED ITEMS: \n");
        List<LibraryItem> sortedItems = new ArrayList<>(database.getCatalogue());
        sortedItems.sort((a, b) -> Integer.compare(b.getBorrowCount(), a.getBorrowCount()));
        for (int i = 0; i < Math.min(5, sortedItems.size()); i++) {
            LibraryItem item = sortedItems.get(i);
            report.append("- ").append(item.getTitle()).append(" (Borrowed ").append(item.getBorrowCount()).append(" times)\n");
        }

        report.append("2. USERS WITH OVERDUE ITEMS: \n");
        LocalDate today = LocalDate.now();
        for (UserAccount user : database.getUsers()) {
            for (LibraryItem item: user.getBorrowingHistory()) {
                if (item.getDueDate() != null && item.getDueDate().isBefore(today)) {
                    report.append("- ").append(user.getName()).append(" (Item: ").append(item.getTitle()).append(")\n");
                }
            }
        }

        report.append("3. CATEGORY DISTRIBUTION:\n");
        int books = 0; int magazines = 0; int journals = 0;
        for (LibraryItem item: database.getCatalogue()) {
            if (item instanceof Book) books++;
             else if (item instanceof Magazine) magazines++;
             else if (item instanceof Journal) journals++;
        }
        report.append("- Books: ").append(books).append("\n");
        report.append("- Magazines: ").append(magazines).append("\n");
        report.append("- Journals: ").append(journals).append("\n");


        return report.toString();
    }

    public void handleUndoAction() {
        database.undoLastAction();
        System.out.println("Last action has been undone successfully.");
    }

    public void saveSystemData() {
        FileHandler.saveCatalogue(database.getCatalogue());
        System.out.println("Catalogue has been saved successfully.");
    }

    public void loadSystemData() {
        ArrayList<LibraryItem> loadedData = FileHandler.loadCatalogue();
        database.setCatalogue(loadedData);

        System.out.println("Catalogue has been loaded successfully.");
    }
}
