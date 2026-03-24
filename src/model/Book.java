package model;

// Subclass extending LibraryItem.
public class Book extends LibraryItem {
    private String isbn;
    private String genre;

    public Book(String id, String title, String author, int year, boolean isAvailable, String isbn, String genre) {
        super(id, title, author, year, isAvailable);
        this.isbn = isbn;
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }


    @Override
    public String getDetails() {
        return "";
    }

    @Override
    public Boolean borrowItem(UserAccount userAccount) {
        return null;
    }

    @Override
    public Boolean returnItem() {
        return null;
    }
}
