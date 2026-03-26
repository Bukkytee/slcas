package model;

import java.util.*;

// The central repository containing the ArrayList to store library items , the Queue for the reservation waitlist , the Stack for undoing the last admin operation , and the fixed-size Array to implement a quick cache for the most frequently accessed items.
public class LibraryDatabase {
    private final ArrayList<LibraryItem> catalogue;
    private final Queue<LibraryItem> waitlist;
    private final Stack<AdminAction> adminHistory;
    private final LibraryItem[] accessCache;

    private static final int CACHE_SIZE = 10;

    public LibraryDatabase() {
        this.catalogue = new ArrayList<>();
        this.waitlist = new LinkedList<>();
        this.adminHistory = new Stack<>();
        this.accessCache = new LibraryItem[CACHE_SIZE];
    }

    public void addItem(LibraryItem item) {
        this.catalogue.add(item);
        adminHistory.push(new AdminAction(ActionType.ADD, item));
        System.out.println("Added LibraryItem: " + item.getTitle());
    }

    public void removeItem(LibraryItem item) {
        if(this.catalogue.remove(item)) {
            adminHistory.push(new AdminAction(ActionType.DELETE, item));
            System.out.println("Deleted LibraryItem: " + item.getTitle());
        } else {
            System.out.println("LibraryItem not found: " + item.getTitle());
        }
    }

    public void undoLastAction() {
        if (!adminHistory.isEmpty()) {
            AdminAction lastAction = adminHistory.pop();

            if (lastAction.actionType == ActionType.ADD) {
                this.catalogue.remove(lastAction.item);
                System.out.println("Undid action: Added " + lastAction.item.getTitle());
            } else if (lastAction.actionType == ActionType.DELETE) {
                this.catalogue.add(lastAction.item);
                System.out.println("Undid action: Deleted " + lastAction.item.getTitle());
            }
        } else {
            System.out.println("No items to undo");
        }
    }

    public ArrayList<LibraryItem> getCatalogue() {
        return catalogue;
    }
    public Queue<LibraryItem> getWaitlist() {
        return waitlist;
    }
    public Stack<AdminAction> getAdminHistory() {
        return adminHistory;
    }
    public LibraryItem[] getAccessCache() {
        return accessCache;
    }
}
