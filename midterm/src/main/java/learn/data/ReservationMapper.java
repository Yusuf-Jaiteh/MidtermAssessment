package learn.data;

import learn.model.Reservation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMapper implements RowMapper<Reservation> {
    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {

        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("reservation_id"));
        reservation.setStartDate(rs.getDate("start_date").toLocalDate());
        reservation.setEndDate((rs.getDate("end_date").toLocalDate()));
        reservation.setTotal(rs.getBigDecimal("total"));
        reservation.setUserId(rs.getInt("guest_user_id"));
        reservation.setLocationId(rs.getInt("location_id"));
        return reservation;
    }
}
