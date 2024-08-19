package learn.data;

import learn.model.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByEmail(String email);
    List<Reservation> findByLocationId(int userId);
    List<Reservation> findByGuestUser(int locationId, int userId);
    Reservation add(Reservation reservation);
    boolean update(Reservation reservation);
    boolean delete(Reservation reservation);
}
