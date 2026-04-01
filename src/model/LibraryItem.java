package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

// The abstract base class for all resources.
public abstract class LibraryItem implements Borrowable, Serializable {
    private String id;
    private String title;
    private String author;
    private int year;
    private boolean isAvailable;
    private int borrowCount = 0;
    private LocalDate dueDate = null;
    private Queue<UserAccount> waitlist = new LinkedList<>();

    public LibraryItem(String id, String title, String author, int year, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.isAvailable = isAvailable;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public abstract String getDetails();

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void addToWaitlist(UserAccount user) {
        waitlist.offer(user);
    }

    public UserAccount getNextInWaitlist() {
        return waitlist.poll();
    }

    public int getWaitlistSize() {
        return waitlist.size();
    }
}
