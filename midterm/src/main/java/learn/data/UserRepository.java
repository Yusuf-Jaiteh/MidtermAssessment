package learn.data;

import learn.model.User;

public interface UserRepository {
    User findById(int id);
    User findByEmail(String email);
    User add(User user);
    boolean update(User user);
    boolean delete(User user);
}
