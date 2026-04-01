package controller;

import model.LibraryDatabase;
import model.LibraryItem;
import model.UserAccount;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Manages the workflow for checking items in and out, calculating overdue charges, and managing th reservation queue
public class BorrowController {
    LibraryDatabase database;
    UserAccount activeUser;

    public BorrowController(LibraryDatabase database) {
        this.database = database;
    }

    public void setActiveUser(UserAccount user) {
        this.activeUser = user;
        if (user != null) {
            System.out.println("Active user has been set to " + user.getName() + "successfully.");
        } else {
            System.out.println("No active user set.");
        }

    }

    public boolean processBorrowRequest(String itemId) {
        if (activeUser == null) {
            System.out.println("Error: Cannot process borrow request. No active user selected.");
            return false;
        }

        for (LibraryItem item : database.getCatalogue()) {
            if (itemId.equalsIgnoreCase(item.getId())) {
                if (item.isAvailable()) {
                    boolean success = item.borrowItem(activeUser);

                    if (success) {
                        item.setDueDate(LocalDate.now().plusDays(14));
                        item.setBorrowCount(item.getBorrowCount() + 1);

                        System.out.println("Success: '" + item.getTitle() + "' has been checked out to " + activeUser.getName() + ".");
                        System.out.println("Due Date: " + item.getDueDate());

                        return true;
                    } else {
                        System.out.println("Error: System failure while checking out '" + item.getTitle() + "'.");
                        return false;
                    }
                } else {
                    System.out.println("Notice: '" + item.getTitle() + "' has been borrowed by someone else.");
                }
            }
        }
        System.out.println("Error: Item with ID [" + itemId + "] could not be found in the database.");
        return false;
    }

    public boolean processReturnRequest(String itemId) {
        if (activeUser == null) {
            System.out.println("Error: Cannot process return request. No active user selected.");
            return false;
        }

        for (LibraryItem item : database.getCatalogue()) {
            if (itemId.equalsIgnoreCase(item.getId())) {
                if (!item.isAvailable()) {
                    double overdueFine = calculateOverdueCharges(item);
                    if (overdueFine > 0) {
                        System.out.println("Warning: '" + item.getTitle() + "' is overdue.");
                        System.out.println("An overdue fine of ₦" + String.format("%.2f", overdueFine) + " has been recorded.");
                        // activeUser.addFine(overdueFine);
                    }
                    activeUser.returnItem(item);
                    boolean success = item.returnItem();
                    if (success) {
                        item.setDueDate(null);
                        System.out.println("Success: '" + item.getTitle() + "' has been returned by " + activeUser.getName() + ".");
                        return true;
                    } else {
                        System.out.println("Error: System failure while returning '" + item.getTitle() + "'.");
                        return false;
                    }
                }  else {
                    System.out.println("Notice: '" + item.getTitle() + "' is already marked as available.");
                    return false;
                }
            }
        }
        System.out.println("Error: Item with ID [" + itemId + "] could not be found in the database.");
        return false;
    }

    public double calculateOverdueCharges(LibraryItem item) {
        if (item.getDueDate() == null || !LocalDate.now().isAfter(item.getDueDate())) {
            return 0.0;
        }

        long daysOverdue = ChronoUnit.DAYS.between(LocalDate.now(), item.getDueDate());

        double dailyCharges = 200.0;
        return daysOverdue * dailyCharges;
    }

    public boolean manageWaitListQueue(String itemId) {
        if (activeUser == null) {
            System.out.println("Error: Cannot manage wait list queue. No active user selected.");
            return false;
        }

        for (LibraryItem item : database.getCatalogue()) {
            if (itemId.equalsIgnoreCase(item.getId())) {
                if (item.isAvailable()) {
                    System.out.println("Notice: '" + item.getTitle() + "' is available. You can borrow it directly instead of joining a waitlist.");
                    return false;
                }

                item.addToWaitlist(activeUser);
                System.out.println("Success: '" + activeUser.getName() + "' has been added to the waitlist for " + item.getTitle() + ".");
                System.out.println("Current position in queue: " + item.getWaitlistSize());
                return true;
            }
        }
        System.out.println("Error: Item with ID [" + itemId + "] could not be found in the database.");
        return false;
    }

}
