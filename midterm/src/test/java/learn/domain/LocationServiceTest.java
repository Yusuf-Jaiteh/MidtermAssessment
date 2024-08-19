package learn.domain;

import learn.model.Location;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class LocationServiceTest {

    LocationService service = new LocationService(new LocationRepositoryDouble());

    @Test
    void shouldFindByHostUserId(){
        assertNotNull(service.findByHostUserId(1));
    }

    @Test
    void shouldNotFindByHostUserId(){
        assertNull(service.findByHostUserId(10000000));
    }

    @Test
    void shouldAdd() {
        Location location = new Location();
        location.setId(1);
        location.setPostalCode("24345");
        location.setCity("MySecCity");
        location.setAddress("BriksTown");
        location.setStandardRate(new BigDecimal(100));
        location.setWeekendRate(new BigDecimal(200));
        location.setStateId(1);
        location.setUserId(1);

        assertNotNull(service.add(location).getPayload());
        assertEquals(location, service.add(location).getPayload());
    }

    @Test
    void shouldNotAddMissingFields(){
        Location location = new Location();
        location.setPostalCode("24345");
        location.setCity("MySecCity");
        location.setAddress("BriksTown");
        location.setStandardRate(new BigDecimal(100));
        location.setWeekendRate(new BigDecimal(200));
        location.setStateId(1);

        assertNull(service.add(location).getPayload());
    }

    @Test
    void shouldUpdate(){
        Location location = new Location();
        location.setId(1);
        location.setPostalCode("24345");
        location.setCity("MySecCity");
        location.setAddress("BriksTown");
        location.setStandardRate(new BigDecimal(100));
        location.setWeekendRate(new BigDecimal(200));
        location.setStateId(1);
        location.setUserId(1);
        service.update(location);
        assertTrue(service.update(location).isSuccess());
    }

    @Test
    void shouldDeleteById(){
        Location location = new Location();
        location.setId(1);
        assertTrue(service.delete(location).isSuccess());
    }

    @Test
    void shouldNotDeleteMissing(){
        Location location = new Location();
        location.setId(10000000);
        assertFalse(service.delete(location).isSuccess());
    }

}
