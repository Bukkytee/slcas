package model;

// The abstract base class for all resources.
public abstract class LibraryItem implements Borrowable {
    private String id;
    private String title;
    private String author;
    private int year;
    private boolean isAvailable;

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
}
