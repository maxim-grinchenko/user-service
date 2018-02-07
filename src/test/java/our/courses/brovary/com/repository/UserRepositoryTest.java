package our.courses.brovary.com.repository;

import org.junit.Before;
import org.junit.Test;
import our.courses.brovary.com.domain.entity.User;
import our.courses.brovary.com.repository.impl.UserRepositoryImpl;
import our.courses.brovary.com.util.TestUtil;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class UserRepositoryTest {
    private UserRepository repository;

    @Before
    public void init() {
        repository = new UserRepositoryImpl();
    }

    @Test
    public void save() {
        User user = repository.save(TestUtil.createUser());
        System.out.println(user.toString());
        assertNotNull(user);
    }

    @Test
    public void update() {
        User user = TestUtil.createUser();
        user.setId(2);
        System.out.println(user);

        user = repository.save(user);
        System.out.println(user.toString());
        assertNotNull(user);
    }

    @Test
    public void deleteById() {
        repository.deleteById(2);
    }

    @Test
    public void deleteAll() {
        repository.deleteAll();
    }

    @Test
    public void findById() {
        User user = repository.findById(7L);
        System.out.println(user.toString());
        assertNotNull(user);
    }

    @Test
    public void findAll() {
        List<User> users = repository.findAll();
        System.out.println(users.toString());
        assertNotNull(users);
    }
}