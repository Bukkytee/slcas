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
        return "Book Details: " +
                "ID=" + getId() +
                ", Title='" + getTitle() + '\'' +
                ", Author='" + getAuthor() + '\'' +
                ", Year=" + getYear() +
                ", ISBN='" + isbn + '\'' +
                ", Genre='" + genre + '\'' +
                ", Available=" + isAvailable();
    }

    @Override
    public Boolean borrowItem(UserAccount userAccount) {
        if (this.isAvailable()) {
           this.setAvailable(false);
           userAccount.borrow(this);
           return true;
        }
        return false;
    }

    @Override
    public Boolean returnItem() {
        if (!this.isAvailable()) {
            this.setAvailable(true);
            return true;
        }
        return false;
    }
}
