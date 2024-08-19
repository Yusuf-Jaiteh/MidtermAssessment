package learn;

import learn.data.LocationJdbcTemplateRepository;
import learn.data.ReservationJdbcTemplateRepository;
import learn.data.UserJdbcTemplateRepository;
import learn.domain.LocationService;
import learn.domain.ReservationService;
import learn.domain.UserService;
import learn.ui.ConsoleIO;
import learn.ui.Controller;
import learn.ui.View;
import org.springframework.jdbc.core.JdbcTemplate;

public class Main {
    public static void main(String[] args) {

        ConsoleIO io = new ConsoleIO();
        View view = new View(io);

        JdbcTemplate jdbcTemplate = DataHelper.getJdbcTemplate();
        LocationJdbcTemplateRepository locationRepository =
                new LocationJdbcTemplateRepository(jdbcTemplate);
        UserJdbcTemplateRepository userRepository =
                new UserJdbcTemplateRepository(jdbcTemplate);
        ReservationJdbcTemplateRepository reservationRepository =
                new ReservationJdbcTemplateRepository(jdbcTemplate);

        LocationService locationService = new LocationService(locationRepository);
        UserService userService = new UserService(userRepository);
        ReservationService reservationService = new ReservationService(reservationRepository);

        Controller controller = new Controller(locationService, userService, reservationService, view);
        controller.run();
    }
}