package learn.domain;

import learn.data.UserRepository;
import learn.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryDouble implements UserRepository {

    private final List<User> users = new ArrayList<>();

    public UserRepositoryDouble(){
        users.add(new User(1, "Tida", "Fofana", "fofanatida@yusuf.com", "(220)2260543"));
        users.add(new User(2, "Yusuf", "Fofana", "Jaitehyusuf@tida.com", "(220)2260543"));
        users.add(new User(3, "Yusuf", "Jaiteh", "yusuftida@Tida.com", "(220)4660543"));
    }
    @Override
    public User findById(int id) {
        return users.stream().filter(i -> i.getId()==id).findFirst().orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return users.stream().filter(i -> i.getEmail()==email).findFirst().orElse(null);
    }

    @Override
    public User add(User user) {
        return user;
    }

    @Override
    public boolean update(User user) {
        return users.stream().filter(i -> i.getId()==user.getId()).findFirst().orElse(null) != null;
    }

    @Override
    public boolean delete(User user) {
        return users.stream().filter(i -> i.getId()==user.getId()).findFirst().orElse(null) != null;
    }
}
