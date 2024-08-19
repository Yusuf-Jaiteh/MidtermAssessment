package learn.data;

import learn.DataHelper;
import learn.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LocationJdbcTemplateRepositoryTest {

    JdbcTemplate jdbcTemplate = DataHelper.getJdbcTemplate();
    LocationJdbcTemplateRepository repository = new LocationJdbcTemplateRepository(jdbcTemplate);

    @BeforeEach
    void setUp(){
        jdbcTemplate.execute("call set_known_good_state();");
    }

    @Test
    void findByHostUserId() {
        Location location = new Location(1, "123 elm st", "los angeles", "90001", new BigDecimal("200.00"), new BigDecimal("250.00"),1,2);
        Location actual = repository.findByHostUserId(1);
        assertEquals(location.getPostalCode(), actual.getPostalCode());
    }

    @Test
    void shouldAdd(){
        Location location = new Location();
        location.setStandardRate(BigDecimal.valueOf(2500));
        location.setWeekendRate(BigDecimal.valueOf(3453));
        location.setCity("Banjul");
        location.setAddress("Brikama");
        location.setUserId(2);
        location.setStateId(3);
        location.setPostalCode("8847");

        assertNotNull(repository.add(location));
        assertEquals(4,location.getId());
    }

    @Test
    void shouldUpdate(){
        Location location = new Location(1, "123 elm st", "Pennsylvania", "9001", BigDecimal.valueOf(200.00), BigDecimal.valueOf(250.00), 1,1);
        assertTrue(repository.update(location));
    }

    @Test
    void shouldDelete(){
        Location location = new Location();
        location.setId(1);
        assertTrue(repository.delete(location));
    }

    @Test
    void shouldNotDelete(){
        Location location = new Location();
        location.setId(4);
        assertFalse(repository.delete(location));
    }
}