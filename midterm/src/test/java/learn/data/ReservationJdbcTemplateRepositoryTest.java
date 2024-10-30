package learn.data;

import learn.DataHelper;
import learn.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationJdbcTemplateRepositoryTest {
    JdbcTemplate jdbcTemplate = DataHelper.getJdbcTemplate();
    ReservationJdbcTemplateRepository repository = new ReservationJdbcTemplateRepository(jdbcTemplate);

    @BeforeEach
    void setUp(){
        jdbcTemplate.execute("call set_known_good_state();");

    }

    @Test
    void findByEmail() {
        Reservation expected = new Reservation(3, LocalDate.of(2024,10,1), LocalDate.of(2025,10,7), new BigDecimal("1050.00"), 2, 1);
        Reservation actual = repository.findByEmail("lvondra0@vkontakte.ru").stream().findFirst().orElse(null);
        assertEquals(expected.getTotal(),actual.getTotal());
    }

    @Test
    void shouldFindByLocationId(){
        Reservation expected = new Reservation(3, LocalDate.of(2024,10,1), LocalDate.of(2025,10,7), new BigDecimal("1050.00"), 2, 1);
        assertEquals(expected.getTotal(), repository.findByLocationId(3).get(0).getTotal());
    }

    @Test
    void shouldAdd() {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2024, 10, 1));
        reservation.setEndDate(LocalDate.of(2025, 10, 7));
        reservation.setTotal(new BigDecimal("1050.00"));
        reservation.setUserId(2);
        reservation.setLocationId(1);
        Reservation result = repository.add(reservation);
        assertNotNull(result);
        assertEquals(4, result.getId());
    }

    @Test
    void shouldUpdate(){
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStartDate(LocalDate.of(2024, 8, 1));
        reservation.setEndDate(LocalDate.of(2025, 8, 5));
        reservation.setTotal(new BigDecimal("1000.00"));
        reservation.setUserId(2);
        reservation.setLocationId(1);
        assertTrue(repository.update(reservation));
    }

    @Test
    void shouldAddReservation(){
        Reservation reservation = new Reservation();
        reservation.setLocationId(1);
        reservation.setUserId(1);
        reservation.setStartDate(LocalDate.of(2020,12, 5));
        reservation.setEndDate(LocalDate.of(2020,12,20));
        reservation.setTotal(BigDecimal.valueOf(650));
        assertNotNull(repository.add(reservation));
    }

    @Test
    void shouldUpdateExistingReservation(){
        Reservation reservation = new Reservation(3,
                LocalDate.of(2024,10, 1),LocalDate.of(2024,10,7),
                BigDecimal.valueOf(1050.00),1,3);
        assertTrue(repository.update(reservation));
    }
    @Test
    void shouldNotUpdateMissingReservation(){
        Reservation reservation = new Reservation(1000000000,
                LocalDate.of(2020,12, 5),LocalDate.of(2020,12,20),
                BigDecimal.valueOf(650),1,1);
        assertFalse(repository.update(reservation));
    }

    @Test
    void shouldDelete(){
        Reservation reservation = new Reservation();
        reservation.setId(1);
        assertTrue(repository.delete(reservation));
    }

}