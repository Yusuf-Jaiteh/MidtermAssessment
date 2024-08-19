package learn.domain;

import learn.data.ReservationRepository;
import learn.model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository {

    List<Reservation> reservations = new ArrayList<>();
    public ReservationRepositoryDouble(){
        reservations.add(new Reservation(1, LocalDate.of(2002,8,30), LocalDate.of(2024, 8,30), new BigDecimal(354), 2, 3));
    }

    @Override
    public List<Reservation> findByEmail(String email) {
        return List.of();
    }

    @Override
    public List<Reservation> findByLocationId(int userId) {
        if(reservations.stream().filter(i -> i.getUserId() == userId).findFirst().orElse(null) == null){
            return List.of();
        }
        return List.of(reservations.stream().filter(i -> i.getUserId() == userId).findFirst().orElse(null));
    }

    @Override
    public List<Reservation> findByGuestUser(int locationId, int userId) {
        return List.of();
    }

    @Override
    public Reservation add(Reservation reservation) {
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) {
        return reservations.stream().filter(i -> i.getId() == reservation.getId()).findFirst().orElse(null) != null;
    }

    @Override
    public boolean delete(Reservation reservation) {
        return reservations.stream().filter(i -> i.getId() == reservation.getId()).findFirst().orElse(null) != null;
    }
}
