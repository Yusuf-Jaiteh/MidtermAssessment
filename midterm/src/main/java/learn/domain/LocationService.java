package learn.domain;

import learn.data.LocationRepository;
import learn.model.Location;

import java.math.BigDecimal;

public class LocationService {
    private final LocationRepository repository;

    public LocationService(LocationRepository repository){
        this.repository = repository;
    }

    public Location findByHostUserId(int id){
        return repository.findByHostUserId(id);
    }

    public Result<Location> add(Location location){
        Result<Location> result = validate(location);

        if(!result.isSuccess()){
            return result;
        }

        result.setPayload(repository.add(location));
        return result;
    }

    public Result<Location> update(Location location){
        Result<Location> result = validate(location);

        if(!result.isSuccess()){
            return result;
        }

        boolean success = repository.update(location);
        if(!success){
            result.addErrorMessage("Location ID " + location.getId() + " not found.");
        } else {
            result.setPayload(location);
        }

        return result;
    }

    public Result<Location> delete(Location location) {
        Result<Location> result = new Result<>();
        if (location == null) {
            result.addErrorMessage("Location cannot be null.");
            return result;
        }

        if (!repository.delete(location)) {
            result.addErrorMessage("Location ID " + location.getId() + " not found.");
        }
        return result;
    }

    private Result<Location> validate(Location location) {
        Result<Location> result = new Result<>();
        if(location == null) {
            result.addErrorMessage("Location cannot be null.");
            return result;
        }

        if(location.getId() <= 0) {
            result.addErrorMessage("Location ID cannot be set for add operation.");
        }

        if(location.getAddress() == null || location.getAddress().isBlank()) {
            result.addErrorMessage("Address is required.");
        }

        if(location.getCity() == null || location.getCity().isBlank()) {
            result.addErrorMessage("City is required.");
        }

        if(location.getPostalCode() == null || location.getPostalCode().isBlank()) {
            result.addErrorMessage("Postal code is required.");
        }

        if(location.getStateId() <= 0) {
            result.addErrorMessage("State ID is required.");
        }

        if(location.getStandardRate() == null || location.getStandardRate().compareTo(new BigDecimal(0)) <= 0) {
            result.addErrorMessage("Standard rate is required.");
        }

        if(location.getWeekendRate() == null || location.getWeekendRate().compareTo(new BigDecimal(0)) <= 0) {
            result.addErrorMessage("Weekend rate is required.");
        }

        if(location.getUserId() <= 0) {
            result.addErrorMessage("User ID is required.");
        }

        return result;
    }
}
