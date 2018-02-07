package our.courses.brovary.com.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import our.courses.brovary.com.domain.entity.User;
import our.courses.brovary.com.repository.impl.UserInMemoryRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static our.courses.brovary.com.util.TestUtil.createUser;

/**
 * @author vasya
 */
public class UserInMemoryRepositoryTest {
    private UserRepository repository;

    @Before
    public void setUp() {
        repository = new UserInMemoryRepository(new ArrayList<>());
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void save() {
        User user = createUser();
        user = repository.save(user);

        assertNotNull(user);

        List<User> users = repository.findAll();
        assertFalse(users.isEmpty());
        assertTrue(users.size() == 1);

        assertEquals(1, users.get(0).getId());
        assertEquals(user.getName(), users.get(0).getName());
        assertEquals(user.getLastName(), users.get(0).getLastName());
    }

    @Test
    public void saveNull() {
        User user;
        user = repository.save(null);

        assertNull(user);
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    public void deleteById() {
        User user = createUser();
        user = repository.save(user);

        repository.deleteById(user.getId());

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    public void existsById() {
        User user = createUser();
        user = repository.save(user);

        assertTrue(repository.existsById(user.getId()));
    }

    @Test
    public void notExistById() {
        assertFalse(repository.existsById(1_000_000L));
    }

    @Test
    public void findAll() {
        repository.save(createUser());
        repository.save(createUser());

        List<User> users = repository.findAll();
        assertFalse(users.isEmpty());
        assertTrue(users.size() == 2);
    }

    @Test
    public void findById() {
        User user = createUser();
        user = repository.save(user);

        User foundUser = repository.findById(user.getId());

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    public void notFoundById() {
        User user = repository.findById(1_000_000L);
        assertNull(user);
    }
}