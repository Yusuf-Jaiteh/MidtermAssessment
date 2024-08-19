package learn.ui;

public enum MainMenuOption {

    EXIT("Exit"),
    //VIEW_LOCATIONS("View Locations"),
    //VIEW_USERS("View Users"),
    VIEW_RESERVATIONS("View Reservations for host Location"),
    //ADD_LOCATION("Add a Location"),
    //ADD_USER("Add a User"),
    ADD_RESERVATION("Make a Reservation"),
    //UPDATE_LOCATION("Update a Location"),
    DELETE_RESERVATION("Cancel a Reservation"),
    UPDATE_RESERVATION("Edit a Reservation");


    private String message;

    private MainMenuOption(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
