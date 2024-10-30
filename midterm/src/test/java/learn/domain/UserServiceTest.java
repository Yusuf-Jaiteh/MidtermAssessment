package learn.domain;

import learn.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService service = new UserService(new UserRepositoryDouble());

    @Test
    void shouldFindById(){
        assertNotNull(service.findByID(1));
    }

    @Test
    void shouldNotFindById(){
        assertNull(service.findByID(10000000));
    }

    @Test
    void shouldAdd(){
        User user = new User();
        user.setEmail("Fofanatiada@gm.com");
        user.setPhone("(220) 2343254");
        user.setFirstName("Tida");
        user.setLastName("Fofana");

        assertNotNull(service.add(user).getPayload());
        assertEquals(user, service.add(user).getPayload());
    }
    //Add another method to verify non-duplicate additions only
    @Test
    void shouldNotAddDuplicates(){
        User user = new User();
        user.setEmail("fofanatida@yusuf.com");
        user.setPhone("(220)2260543");
        user.setFirstName("Tida");
        user.setLastName("Fofana");

        assertNull(service.add(user).getPayload());
    }

    @Test
    void shouldNotAddMissingFields(){
        User user = new User();
        user.setEmail("Fofanatiada@gm.com");
        user.setPhone("(220) 2343254");
        user.setFirstName("Tida");

        assertNull(service.add(user).getPayload());

    }

    @Test
    void shouldNotUpdateDuplicate(){
        User user = new User();
        user.setId(1);
        user.setEmail("fofanatida@yusuf.com");
        user.setPhone("(220) 2343254");
        user.setFirstName("Tida");
        user.setLastName("Fofana");

        assertFalse(service.update(user).isSuccess());
    }

    @Test
    void
    shouldUpdate() {
        User user = new User();
        user.setId(1);
        user.setEmail("jdjfgkjhsjk@gmail.com");
        user.setPhone("(220) 666666");
        user.setFirstName("Sumaiyyah");
        user.setLastName("Fofana");

        assertTrue(service.update(user).isSuccess());
    }

    @Test
    void shouldDeleteById(){
        assertTrue(service.delete(new User(1, "Tida", "Fofana", "fofanatida@yusuf.com", "(220)2260543")).isSuccess());
    }

    @Test
    void shouldDeleteMissingId(){
        assertFalse(service.delete(new User(1000000, "Tida", "Fofana",
                "fofanatida@yusuf.com", "(220)2260543")).isSuccess());
    }

}