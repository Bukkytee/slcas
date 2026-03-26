package model;

// Subclass extending LibraryItem.
public class Journal extends LibraryItem {
    private String volume;
    private String fieldOfStudy;

    public String getVolume() {
        return volume;
    }
    public void setVolume(String volume) {
        this.volume = volume;
    }
    public String getFieldOfStudy() {
        return fieldOfStudy;
    }
    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public Journal(String id, String title, String author, int year, boolean isAvailable, String volume, String fieldOfStudy) {
        super(id, title, author, year, isAvailable);
        this.volume = volume;
        this.fieldOfStudy = fieldOfStudy;
    }

    @Override
    public String getDetails() {
        return "Journal Details: " +
                "ID=" + getId() +
                ", Title='" + getTitle() + '\'' +
                ", Author='" + getAuthor() + '\'' +
                ", Year=" + getYear() +
                ", Volume='" + volume + '\'' +
                ", Field of Study='" + fieldOfStudy + '\'' +
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
