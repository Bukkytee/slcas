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
