package learn.domain;

import learn.data.LocationRepository;
import learn.model.Location;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LocationRepositoryDouble implements LocationRepository {

    private final List<Location> locations = new ArrayList<>();

    public LocationRepositoryDouble(){
        locations.add(new Location(1, "locationLo", "MyCity", "1235", new BigDecimal(100), new BigDecimal(200), 1, 1));
        locations.add(new Location(2, "Lolocation", "CityMy", "3512", new BigDecimal(400), new BigDecimal(300), 2, 2));
    }
    @Override
    public Location findByHostUserId(int id) {
        return locations.stream().filter(i -> i.getId()==id).findFirst().orElse(null);
    }

    @Override
    public Location add(Location location) {
        return location;
    }

    @Override
    public boolean update(Location location) {
        return locations.stream().filter(i -> i.getId()==location.getId()).findFirst().orElse(null) != null;
    }

    @Override
    public boolean delete(Location location) {
        return locations.stream().filter(i -> i.getId()==location.getId()).findFirst().orElse(null) != null;
    }
}
