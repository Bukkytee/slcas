package model;

// The interface defining borrowing actions.
public interface Borrowable {
    Boolean borrowItem(UserAccount userAccount);
    Boolean returnItem();
}
