package learn.data;

import learn.model.Location;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;

public class LocationJdbcTemplateRepository implements LocationRepository{
    private final JdbcTemplate jdbcTemplate;

    public LocationJdbcTemplateRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Location findByHostUserId(int id) {
        String sql = """
                select * from location 
                where user_id = ?;
                """;
        return jdbcTemplate.query(sql, new LocationMapper(), id).stream().findFirst().orElse(null);
    }

    @Override
    public Location add(Location location){
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("location").usingGeneratedKeyColumns("location_id").
                usingColumns("user_id", "address", "city", "postal_code", "state_id", "standard_rate", "weekend_rate");

        HashMap<String, Object> args = new HashMap<>();
        args.put("user_id", location.getUserId());
        args.put("address", location.getAddress());
        args.put("city", location.getCity());
        args.put("postal_code", location.getPostalCode());
        args.put("state_id", location.getStateId());
        args.put("standard_rate", location.getStandardRate());
        args.put("weekend_rate", location.getWeekendRate());

        int id = insert.executeAndReturnKey(args).intValue();
        location.setId(id);
        return location;
    }

    @Override
    public boolean update(Location location){
        final String sql = """
                update location set
                user_id = ?,
                address = ?,
                city = ?,
                postal_code = ?,
                state_id = ?,
                standard_rate = ?,
                weekend_rate = ?
                where location_id = ?;
                """;
        return jdbcTemplate.update(sql, location.getUserId(), location.getAddress(), location.getCity(), location.getPostalCode(), location.getStateId(), location.getStandardRate(), location.getWeekendRate(), location.getId()) > 0;
    }

    @Override
    public boolean delete(Location location){
        jdbcTemplate.update("delete from reservation where location_id = ?", location.getId());
        final String sql = """
                delete from location
                where location_id = ?;
                """;
        return jdbcTemplate.update(sql,location.getId()) > 0;
    }
}
