package learn.data;

import learn.DataHelper;
import learn.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

class UserJdbcTemplateRepositoryTest {
    JdbcTemplate jdbcTemplate = DataHelper.getJdbcTemplate();
    UserJdbcTemplateRepository repository = new UserJdbcTemplateRepository(jdbcTemplate);

    @BeforeEach
    void setUp(){
        jdbcTemplate.execute("call set_known_good_state();");
        //jdbcTemplate.execute("DELETE FROM `user`;");
        //jdbcTemplate.execute("INSERT INTO `user` (user_id, first_name, last_name, email, phone) VALUES (1, 'Yusuf', 'Jaiteh', 'yusufj@gmail.com', '3245335');");
    }

    @Test
    void shouldFindIdOne(){
        User expected = new User(1,"llywellyn", "vondra","lvondra0@vkontakte.ru", "(940) 3612277");
        User actual = repository.findById(1);
        assertEquals(expected.getEmail(), actual.getEmail());
        assertNotNull(actual);
    }

    @Test
    void shouldNotFindMissingId(){
        User expected = new User();
        expected.setId(1234);
        assertNull(repository.findById(1234));
        assertNotEquals(expected, repository.findById(expected.getId()));
    }

    @Test
    void shouldFindExistingEmail(){
        User expected = new User();
        expected.setEmail("rklimpt1@paginegialle.it");
        assertNotNull(repository.findByEmail(expected.getEmail()));
        assertEquals(expected.getEmail(),repository.findByEmail("rklimpt1@paginegialle.it").getEmail());
    }

    @Test
    void shouldNotFindNonExisting(){
        assertNull(repository.findByEmail("yusufj235@gmail.com"));
    }

    @Test
    void shouldAdd(){
        User user = new User();
        user.setFirstName("Yusuf");
        user.setLastName("Jaiteh");
        user.setPhone("4793752345");
        user.setEmail("yusufj@gmail.com");

        User result = repository.add(user);
        assertNotNull(result);
        assertEquals(4,user.getId());
    }

    @Test
    void shouldUpdate(){
        User user = new User(1,"Muhammed", "Dibba", "muhammedD@gmail.com","987345");
        assertTrue(repository.update(user));
    }

    @Test
    void shouldDelete(){
        User user = new User();
        user.setId(1);
        assertTrue(repository.delete(user));
    }

    @Test
    void shouldNotDelete(){
        User user = new User();
        user.setId(4);
        assertFalse(repository.delete(user));
    }
}