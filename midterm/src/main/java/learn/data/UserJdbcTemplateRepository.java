package learn.data;

import learn.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;

public class UserJdbcTemplateRepository implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public User findById(int id) {
        final String sql = """
                select * from `user`
                where user_id = ?;
                """;
        return jdbcTemplate.query(sql, new UserMapper(), id).stream().findFirst().orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        final String sql = """
                select * from `user`
                where email = ?;
                """;
        return jdbcTemplate.query(sql, new UserMapper(), email).stream().findFirst().orElse(null);
    }


    @Override
    public User add(User user){
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("`user`").usingGeneratedKeyColumns("user_id").
                usingColumns("first_name","last_name","email","phone");

        HashMap<String, Object> args = new HashMap<>();
        args.put("first_name", user.getFirstName());
        args.put("last_name", user.getLastName());
        args.put("email", user.getEmail());
        args.put("phone", user.getPhone());

        int id = insert.executeAndReturnKey(args).intValue();
        user.setId(id);
        return user;
    }

    @Override
    public boolean update(User user){
        final String sql = """
                update `user` set
                first_name = ?,
                last_name = ?,
                email = ?,
                phone = ?
                where user_id = ?;
                """;
        return jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getId()) > 0;
    }

    @Override
    public boolean delete(User user){
        jdbcTemplate.update("delete from reservation where guest_user_id = ?", user.getId());
        jdbcTemplate.update("delete r from reservation r inner join location l on r.location_id = l.location_id");
        jdbcTemplate.update("delete from location where user_id = ?;", user.getId());
        final String sql = """
                delete from `user` 
                where user_id = ?;
                """;
        return jdbcTemplate.update(sql,user.getId()) > 0;
    }
}
