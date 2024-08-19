package learn.domain;

import learn.data.UserRepository;
import learn.model.Reservation;
import learn.model.User;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public User findByID(int id){
        return repository.findById(id);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Result<User> add(User user){
        Result<User> result = validate(user);
        if(!result.isSuccess()){
            return result;
        }

        result.setPayload(repository.add(user));
        return result;
    }

    public Result<User> update(User user){
        Result<User> result = validate(user);
        if(!result.isSuccess()){
            return result;
        }

        boolean success = repository.update(user);
        if(!success){
            result.addErrorMessage("User ID " + user.getId() + " not found.");
        } else {
            result.setPayload(user);
        }

        return result;
    }

    public Result<User> delete(User user){
        Result<User> result = new Result<>();
        if(user == null){
            result.addErrorMessage("User cannot be null.");
            return result;
        }

        if(!repository.delete(user)){
            result.addErrorMessage("User ID " + user.getId() + " not found.");
        }
        return result;
    }

    private Result<User> validate(User user) {
        Result<User> result = new Result<>();
        if(user == null){
            result.addErrorMessage("User cannot be null.");
            return result;
        }

        if(user.getFirstName() == null || user.getFirstName().isBlank()){
            result.addErrorMessage("First Name is required.");
        }

        if(user.getLastName() == null || user.getLastName().isBlank()){
            result.addErrorMessage("Last Name is required.");
        }

        if(user.getEmail() == null || user.getEmail().isBlank()){
            result.addErrorMessage("Email is required.");
        } else if (repository.findByEmail(user.getEmail()) != null) {
            result.addErrorMessage("Email cannot be a duplicate.");
        } //else if(the emaial exists in the database){add error message}

        if(user.getPhone() == null || user.getPhone().isBlank()){
            result.addErrorMessage("Phone is required.");
        }

        return result;
    }

}
