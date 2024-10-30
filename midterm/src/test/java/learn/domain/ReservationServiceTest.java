package learn.domain;

import learn.model.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    ReservationService service = new ReservationService(new ReservationRepositoryDouble());

//    @Test
//    void shouldFindByEmail(){
//        assertNotNull(service.findByEmail("lvondra0@vkontakte.ru").stream().findFirst().orElse(null));
//    }
    @Test
    void shouldFindByID(){
        assertNotNull(service.findByLocationId(2));
    }

    @Test
    void shouldNotFindByID(){
        assertNotNull(service.findByLocationId(10000000));
    }

    @Test
    void shouldAdd(){
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setLocationId(1);
        reservation.setUserId(3);
        reservation.setTotal(new BigDecimal(5433));
        reservation.setEndDate(LocalDate.of(2025,10,31));
        reservation.setStartDate(LocalDate.of(2024,10,31));

        assertTrue(service.add(reservation).isSuccess());
        assertNotNull(service.add(reservation).getPayload());
        assertEquals(reservation, service.add(reservation).getPayload());
    }

    @Test
    void shouldNotAddMissingFields(){
        Reservation reservation = new Reservation();
        reservation.setLocationId(1);
        reservation.setUserId(3);
        reservation.setTotal(new BigDecimal(5433));
        reservation.setEndDate(LocalDate.of(2023,10,31));

        assertNull(service.add(reservation).getPayload());
    }

    @Test
    void shouldUpdateExisting(){
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStartDate(LocalDate.of(2024, 8,30));
        reservation.setEndDate(LocalDate.of(2024, 9,30));
        reservation.setUserId(1);
        reservation.setTotal(BigDecimal.valueOf(354));

        assertTrue(service.update(reservation).isSuccess());
    }

    @Test
    void shouldNotUpdateExisting(){
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStartDate(LocalDate.of(2023, 8,30));
        reservation.setEndDate(LocalDate.of(2027, 9,30));
        reservation.setUserId(1);

        assertFalse(service.update(reservation).isSuccess());
    }

    @Test
    void shouldDelete(){
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStartDate(LocalDate.of(2024, 8,30));
        reservation.setEndDate(LocalDate.of(2024, 9,30));
        reservation.setUserId(1);

        assertTrue(service.delete(reservation).isSuccess());
    }

    @Test
    void shouldNotDeleteMissingId(){
        Reservation reservation = new Reservation();
        reservation.setId(1000000);
        reservation.setStartDate(LocalDate.of(2023, 8,30));
        reservation.setEndDate(LocalDate.of(2027, 9,30));
        reservation.setUserId(1);

        assertFalse(service.delete(reservation).isSuccess());
    }

    @Test
    void shouldNotDeletePastReservation(){
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStartDate(LocalDate.of(2020, 8,30));
        reservation.setEndDate(LocalDate.of(2021, 9,30));
        reservation.setUserId(1);

        assertFalse(service.delete(reservation).isSuccess());
    }
}
