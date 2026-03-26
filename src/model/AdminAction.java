package model;

public class AdminAction {
    ActionType actionType;
    LibraryItem item;

    public AdminAction(ActionType actionType, LibraryItem item) {
        this.actionType = actionType;
        this.item = item;
    }
}
