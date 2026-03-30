package model;

import utils.IDGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Class to hold user details and their borrowing history.
public class UserAccount implements Serializable {
    private String userId;
    private String name;
    private List<LibraryItem> borrowingHistory;

    public UserAccount(String userId, String name, List<LibraryItem> borrowingHistory) {
        this.userId = IDGenerator.generateUserID();
        this.name = name;
        this.borrowingHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<LibraryItem> getBorrowingHistory() {
        return borrowingHistory;
    }
    public void setBorrowingHistory(List<LibraryItem> borrowingHistory) {
        this.borrowingHistory = borrowingHistory;
    }

    public void borrow(LibraryItem item) {
        this.borrowingHistory.add(item);
    }
    public void returnItem(LibraryItem item) {
        this.borrowingHistory.remove(item);
    }
}
