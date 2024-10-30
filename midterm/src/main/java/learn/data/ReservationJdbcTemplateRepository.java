package learn.data;

import learn.model.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;

public class ReservationJdbcTemplateRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcTemplateRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findByEmail(String email){
        final String sql = """
                select r.reservation_id,r.start_date,r.guest_user_id,r.location_id,r.end_date,r.total, u.first_name,u.last_name,u.email
                from reservation r
                inner join `user` u on r.guest_user_id = u.user_id
                where u.email = ?
                order by r.start_date asc;
                """;
        return jdbcTemplate.query(sql, new ReservationMapper(), email);
    }

    @Override
    public List<Reservation> findByLocationId(int locationId) {
        final String sql = """
                select * from reservation
                where location_id = ?
                order by start_date asc;
                """;
        return jdbcTemplate.query(sql, new ReservationMapper(), locationId);
    }

    @Override
    public List<Reservation> findByGuestUser(int locationId, int userId) {
        final String sql = """
                select * from reservation
                where location_id = ? and guest_user_id = ?
                order by start_date asc;
                """;
        return jdbcTemplate.query(sql, new ReservationMapper(), locationId, userId);
    }

    @Override
    public Reservation add(Reservation reservation) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("reservation").usingGeneratedKeyColumns("reservation_id").
                usingColumns("start_date", "end_date", "total", "guest_user_id", "location_id");;

        HashMap<String, Object> args = new HashMap<>();
        args.put("start_date", reservation.getStartDate());
        args.put("end_date", reservation.getEndDate());
        args.put("total", reservation.getTotal());
        args.put("guest_user_id", reservation.getUserId());
        args.put("location_id", reservation.getLocationId());

        int id = insert.executeAndReturnKey(args).intValue();
        reservation.setId(id);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) {
        final String sql = """
                update reservation set
                start_date = ?,
                end_date = ?,
                total = ?               
                where reservation_id = ?;
                """;
        return jdbcTemplate.update(sql, reservation.getStartDate(), reservation.getEndDate(), reservation.getTotal(), reservation.getId()) > 0;
    }

    @Override
    public boolean delete(Reservation reservation) {
        final String sql = """
                delete from reservation
                where reservation_id = ?;
                """;
        return jdbcTemplate.update(sql, reservation.getId()) > 0;
    }

    {

    }


}
