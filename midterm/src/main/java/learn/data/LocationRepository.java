package learn.data;

import learn.model.Location;

public interface LocationRepository {
    Location findByHostUserId(int id);

    Location add(Location location);

    boolean update(Location location);

    boolean delete(Location location);
}
