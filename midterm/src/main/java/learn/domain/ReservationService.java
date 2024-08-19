package learn.domain;

import learn.data.ReservationRepository;
import learn.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository){
        this.repository = repository;
    }

    public List<Reservation> findByEmail(String email){
        return repository.findByEmail(email);
    }

    public List<Reservation> findByLocationId(int locationId){
        return repository.findByLocationId(locationId);
    }

    public List<Reservation> findByGuestUser(int locationId, int userId){
        return repository.findByGuestUser(locationId, userId);
    }

    public Result<Reservation> add(Reservation reservation){
        Result<Reservation> result = validate(reservation);
        if(!result.isSuccess()){
            return result;
        }

        result.setPayload(repository.add(reservation));

        return result;
    }

    public Result<Reservation> update(Reservation reservation){
        Result<Reservation> result = validate(reservation);
        if(!result.isSuccess()){
            return result;
        }

        boolean success = repository.update(reservation);
        if(!success){
            result.addErrorMessage("Reservation Update failed!");
        }
        return result;
    }

    public Result<Reservation> delete(Reservation reservation){
        Result<Reservation> result = new Result<>();
        if(reservation.getEndDate().isBefore(LocalDate.now())){
            result = new Result<>();
            result.addErrorMessage("Cannot delete a reservation that has already ended.");
        }
        if(!result.isSuccess()){
            return result;
        }

        boolean success = repository.delete(reservation);
        if(!success){
            result.addErrorMessage("Reservation Delete failed!");
        }
        return result;
    }

    private Result<Reservation> validate(Reservation reservation) {
        
        Result<Reservation> result = validateNulls(reservation);
        if(!result.isSuccess()){
            return result;
        }

        validateFields(reservation, result);
        return result;
    }

    private void validateFields(Reservation reservation, Result<Reservation> result) {
        if(reservation.getStartDate().isAfter(reservation.getEndDate())){
            result.addErrorMessage("Start Date must be before the end date.");
        }

        if(!reservation.getStartDate().isAfter(LocalDate.now())){
            result.addErrorMessage("Start date must be in the future.");
        }

        if(repository.findByLocationId(reservation.getUserId()).stream().anyMatch(r -> r.getStartDate().isBefore(reservation.getEndDate()) && r.getEndDate().isAfter(reservation.getStartDate()))){
            result.addErrorMessage("User already has a reservation that overlaps with this date range.");
        }


    }

    private Result<Reservation> validateNulls(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        if (reservation == null) {
            result.addErrorMessage("Reservation cannot be null.");
            return result;
        }

        if(reservation.getStartDate() == null){
            result.addErrorMessage("Start Date is required.");
        }

        if(reservation.getEndDate() == null){
            result.addErrorMessage("End Date is required.");
        }

        if(reservation.getUserId() <= 0){
            result.addErrorMessage("User ID is required.");
        }

        if(reservation.getTotal() == null){
            result.addErrorMessage("The total is required.");
        }

        return result;
    }
}
