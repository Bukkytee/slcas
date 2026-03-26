package model;

// Subclass extending LibraryItem.
public class Magazine extends LibraryItem {
    private int issueNumber;

    public Magazine(String id, String title, String author, int year, boolean isAvailable, int issueNumber) {
        super(id, title, author, year, isAvailable);
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }
    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    @Override
    public String getDetails() {
        return "Magazine Details: " +
                "ID=" + getId() +
                ", Title='" + getTitle() + '\'' +
                ", Author='" + getAuthor() + '\'' +
                ", Year=" + getYear() +
                ", Issue Number='" + issueNumber + '\'' +
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
