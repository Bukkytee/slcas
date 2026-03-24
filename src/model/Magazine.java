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
