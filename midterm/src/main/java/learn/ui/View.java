package learn.ui;

import learn.model.Location;
import learn.model.Reservation;
import learn.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");

        MainMenuOption[] values = MainMenuOption.values();
        for (int i = 0; i < values.length; i++) {
            io.printf("%s. %s%n", i, values[i].getMessage());
        }
        String msg = String.format("Select [0-%s]: ", values.length - 1);
        int index = io.readInt(msg, 0, values.length - 1);
        io.println();
        return values[index];
    }

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }
    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

    public String getHostEmail() {
        return io.readRequiredString("HOST EMAIL: ");
    }

    public void displayStatus(boolean successStatus, String message) {
        displayStatus(successStatus, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }

    public void displayHostDetails(User hostUser, Location hostLocation){
        System.out.println();
        System.out.println(hostUser.getFirstName());
        System.out.println(hostLocation.getAddress());
        System.out.println(hostLocation.getCity()+", "+hostLocation.getPostalCode());
        System.out.println("=".repeat(hostLocation.getAddress().length()));
    }

    public void displayReservations(Reservation reservation,  User guestUser) {
        if (reservation == null){
            System.out.println("No reservation was found.");
            return;
        }

            System.out.printf("ID: %s, %s - %s, Guest: %s, %s, Email: %s%n",
                    reservation.getId(),
                    reservation.getStartDate().format(io.getFormater()),
                    reservation.getEndDate().format(io.getFormater()),
                    guestUser.getFirstName(),
                    guestUser.getLastName(),
                    guestUser.getEmail());
    }

    public String getGuestEmail() {
        return io.readRequiredString("GUEST EMAIL: ");
    }

    public LocalDate getStartDate(String message, Reservation toBeUpdated) {
        return io.readUnrequiredStartLocalDate(message+toBeUpdated.getStartDate()+"): ", toBeUpdated);
    }

    public LocalDate getEndDate(String message, Reservation toBeUpdated) {
        return io.readUnrequiredEndLocalDate(message+toBeUpdated.getStartDate()+"): ", toBeUpdated);
    }

    public LocalDate getStartDate() {
        return io.readLocalDate("Start (MM/dd/yyyy): ");
    }

    public LocalDate getEndDate() {
        return io.readLocalDate("End (MM/dd/yyyy): ");
    }

    public void displayMessage(String s) {
        io.println(s);
    }

    public Reservation createReservation(LocalDate startDate, LocalDate endDate, Location hostLocation, User user, BigDecimal total) {
        Reservation reservation = new Reservation();
        reservation.setLocationId(hostLocation.getId());
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setUserId(user.getId());
        reservation.setTotal(total);
        return reservation;
    }


    public String format(LocalDate toFormat){
        return toFormat.format(io.getFormater());
    }

    public DateTimeFormatter getFormmater(){
        return io.getFormater();
    }

    public String getStringInput(String s) {
        return io.readRequiredString(s);
    }

    public int getIntInput(String s) {
        return io.readInt(s);
    }

    public Reservation updateReservation(LocalDate startDate, LocalDate endDate, BigDecimal total, Reservation toBeUpdated) {
        toBeUpdated.setTotal(total);
        toBeUpdated.setStartDate(startDate);
        toBeUpdated.setEndDate(endDate);
        return toBeUpdated;
    }
}
