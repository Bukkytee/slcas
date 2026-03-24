package model;

import java.util.List;

// Class to hold user details and their borrowing history.
public class UserAccount {
    private String userId;
    private String name;
    private List<LibraryItem> borrowingHistory;

    public UserAccount(String userId, String name, List<LibraryItem> borrowingHistory) {
        this.userId = userId;
        this.name = name;
        this.borrowingHistory = borrowingHistory;
    }
}
