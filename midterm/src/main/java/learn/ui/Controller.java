package learn.ui;

import learn.domain.LocationService;
import learn.domain.ReservationService;
import learn.domain.Result;
import learn.domain.UserService;
import learn.model.Location;
import learn.model.Reservation;
import learn.model.User;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class Controller {

    private final LocationService locationService;
    private final UserService userService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(LocationService locationService, UserService userService, ReservationService reservationService, View view) {
        this.locationService = locationService;
        this.userService = userService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Welcome to Don't Wreck My House Reservation Service");
        try {
            runAppLoop();
        } catch (Exception ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runAppLoop() {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
//                case VIEW_LOCATIONS:
//                    viewLocations();
//                    break;
//                case VIEW_USERS:
//                    viewUsers();
//                    break;
                case VIEW_RESERVATIONS:
                    viewReservations();
                    break;
//                case ADD_USER:
//                    addUser();
//                    break;
                case ADD_RESERVATION:
                    addReservation();
                    break;
//                case UPDATE_LOCATION:
//                    updateLocation();
//                    break;
//                case UPDATE_USER:
//                    updateUser();
//                    break;
                case UPDATE_RESERVATION:
                    updateReservation();
                    break;
                case DELETE_RESERVATION:
                    deleteReservation();
                    break;

            }
        } while (option != MainMenuOption.EXIT);
    }

    private void deleteReservation() {
        Location hostLocation;
        User user;
        view.displayHeader(MainMenuOption.DELETE_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        String hostEmail = view.getHostEmail();

        User hostUser = userService.findByEmail(hostEmail);
        if(hostUser == null){
            view.displayStatus(false, String.format("This host is not found in the system"));
        }else {
            hostLocation = locationService.findByHostUserId(hostUser.getId());
            if(hostLocation == null){
                view.displayStatus(false, String.format("This user is not a host!."));
            }else {
                user = userService.findByEmail(guestEmail);
                List<Reservation> reservations = reservationService.findByGuestUser(hostLocation.getId(), user.getId());
                if (user == null){
                    view.displayStatus(false, String.format("This guest is not found in the system!"));
                }else {
                    if (reservations != null && !reservations.isEmpty()) {
                        view.displayHostDetails(hostUser, hostLocation);
                        for (Reservation reservation : reservations) {
                            User guestUser = userService.findByID(reservation.getUserId());
                            view.displayReservations(reservation, guestUser);
                        }
                    }

                    Reservation toBeDeleted = null;
                    int reservationId = view.getIntInput("Reservation ID: ");
                    for (Reservation reservation : reservations) {
                        if(reservation.getId() == reservationId){
                            toBeDeleted = reservation;
                            break;
                        }
                    }

                    String response = view.getStringInput("Do you want to proceed? [y/n]: ");

                    if (response.toLowerCase().charAt(0) == 'y'){
                        Result<Reservation> result = reservationService.delete(toBeDeleted);

                        if (!result.isSuccess()) {
                            view.displayStatus(false, result.getErrorMessages());
                        } else {
                            String successMessage = String.format("Reservation %s successfully deleted!.", reservationId);
                            view.displayStatus(true, successMessage);
                        }
                    }

                }

            }

        }



    }

    private void updateReservation() {
        LocalDate startDate;
        LocalDate endDate;
        Location hostLocation;
        User user;
        view.displayHeader(MainMenuOption.UPDATE_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        String hostEmail = view.getHostEmail();

        User hostUser = userService.findByEmail(hostEmail);
        if(hostUser == null){
            view.displayStatus(false, String.format("This host is not found in the system"));
        }else {
            hostLocation = locationService.findByHostUserId(hostUser.getId());
            if(hostLocation == null){
                view.displayStatus(false, String.format("This user is not a host!."));
            }else {
                user = userService.findByEmail(guestEmail);
                List<Reservation> reservations = reservationService.findByGuestUser(hostLocation.getId(),user.getId());
                if (user == null){
                    view.displayStatus(false, String.format("This guest is not found in the system!"));
                }else {
                    if (reservations != null && !reservations.isEmpty()) {
                        view.displayHostDetails(hostUser, hostLocation);
                        for (Reservation reservation : reservations) {
                            User guestUser = userService.findByID(reservation.getUserId());
                            view.displayReservations(reservation, guestUser);
                        }
                    }

                    Reservation toBeUpdated = null;
                    int reservationId = view.getIntInput("Reservation ID: ");
                    for (Reservation reservation : reservations) {
                        if(reservation.getId() == reservationId){
                            toBeUpdated = reservation;
                            break;
                        }
                    }

                    view.displayHeader("Editing Reservation "+ reservationId);

                    startDate = view.getStartDate("Start (", toBeUpdated);
                    endDate = view.getEndDate("End (", toBeUpdated);
                    view.displayHeader("Summary");
                    view.displayMessage("Start: "+startDate.format(view.getFormmater()));
                    view.displayMessage("End: "+endDate.format(view.getFormmater()));
                    BigDecimal total = getCalculatedTotal(startDate, endDate, hostLocation);
                    view.displayMessage("Total: $"+total);
                    String response = view.getStringInput("Is this okay? [y/n]: ");

                    Reservation updated = view.updateReservation(startDate, endDate, total, toBeUpdated);
                    if (response.toLowerCase().charAt(0) == 'y'){
                        Result<Reservation> result = reservationService.update(updated);

                        if (!result.isSuccess()) {
                            view.displayStatus(false, result.getErrorMessages());
                        } else {
                            String successMessage = String.format("Reservation %s successfully updated!.", reservationId);
                            view.displayStatus(true, successMessage);
                        }
                    }
                }

            }

        }
    }

//    private void updateUser() {
//        System.out.println("This is trying to update a User");
//    }
//
//    private void updateLocation() {
//        System.out.println("This is trying to update a location");
//    }

    private void addReservation() {
        LocalDate startDate;
        LocalDate endDate;
        Location hostLocation;
        User user;
        view.displayHeader(MainMenuOption.ADD_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        String hostEmail = view.getHostEmail();

        User hostUser = userService.findByEmail(hostEmail);
        if(hostUser == null){
            view.displayStatus(false, String.format("This host is not found in the system"));
        }else {
            hostLocation = locationService.findByHostUserId(hostUser.getId());
            if(hostLocation == null){
                view.displayStatus(false, String.format("This user is not a host!."));
            }else {
                user = userService.findByEmail(guestEmail);
                List<Reservation> reservations = reservationService.findByGuestUser(hostLocation.getId(), user.getId());
                if (user == null){
                    view.displayStatus(false, String.format("This guest is not found in the system!"));
                }else {
                    if (reservations != null && !reservations.isEmpty()) {
                        view.displayHostDetails(hostUser, hostLocation);
                        for (Reservation reservation : reservations) {
                            User guestUser = userService.findByID(reservation.getUserId());
                            view.displayReservations(reservation, guestUser);
                        }
                    }

                    startDate = view.getStartDate();
                    endDate = view.getEndDate();
                    view.displayHeader("Summary");
                    view.displayMessage("Start: "+startDate.format(view.getFormmater()));
                    view.displayMessage("End: "+endDate.format(view.getFormmater()));
                    BigDecimal total = getCalculatedTotal(startDate, endDate, hostLocation);
                    view.displayMessage("Total: $"+total);
                    String response = view.getStringInput("Is this okay? [y/n]: ");

                    Reservation createdReservation = view.createReservation(startDate, endDate,hostLocation,user,total);
                    if (response.toLowerCase().charAt(0) == 'y'){
                        Result<Reservation> result = reservationService.add(createdReservation);

                        if (!result.isSuccess()) {
                            view.displayStatus(false, result.getErrorMessages());
                        } else {
                            String successMessage = String.format("Reservation %s successfully made.", result.getPayload().getId());
                            view.displayStatus(true, successMessage);
                        }
                    }
                }

            }

        }

    }

    private BigDecimal getCalculatedTotal(LocalDate startDate, LocalDate endDate, Location hostLocation) {
        BigDecimal total = BigDecimal.ZERO;
        for(LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)){
            if(DayOfWeek.FRIDAY.equals(date.getDayOfWeek()) || DayOfWeek.SATURDAY.equals(date.getDayOfWeek())){
                total = total.add(hostLocation.getWeekendRate());
            } else {
                total = total.add(hostLocation.getStandardRate());
            }
        }
        return total;
    }
//bbiesterfeld5@1und1.de
//thonnan2@berkeley.edu
//    private void addUser() {
//        System.out.println("This is trying to add a User");
//    }

    private void viewReservations() {
        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS.getMessage());

        String email = view.getHostEmail();
        User hostUser = userService.findByEmail(email);
        if(hostUser == null){
            view.displayStatus(false, String.format("This host is not found in the system"));
        }else {
            Location hostLocation = locationService.findByHostUserId(hostUser.getId());
            if(hostLocation == null){
                view.displayStatus(false, String.format("This user is not a host!."));
            }else {
                List<Reservation> reservations = reservationService.findByLocationId(hostLocation.getId());

                if (reservations == null || reservations.isEmpty()){
                    view.displayStatus(false, String.format("There is no reservation found with host email: '%S'.", email));
                } else {
                    view.displayHostDetails(hostUser, hostLocation);
                    for(Reservation reservation : reservations){
                        User guestUser =  userService.findByID(reservation.getUserId());
                        view.displayReservations(reservation, guestUser);
                    }
                }
            }
        }


    }

//    private void viewUsers() {
//        System.out.println("This is trying to view users");
//    }
//
//    private void viewLocations() {
//        System.out.println("This is trying to view locations");
//    }
}
