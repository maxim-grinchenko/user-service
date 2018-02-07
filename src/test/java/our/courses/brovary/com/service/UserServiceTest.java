package our.courses.brovary.com.service;

import org.junit.Before;
import org.junit.Test;
import our.courses.brovary.com.domain.entity.User;
import our.courses.brovary.com.infra.exception.ValidationException;
import our.courses.brovary.com.repository.UserRepository;
import our.courses.brovary.com.repository.impl.UserInMemoryRepository;
import our.courses.brovary.com.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static our.courses.brovary.com.util.TestUtil.createUser;

public class UserServiceTest {
    private UserService service;
    private UserRepository repository;

    @Before
    public void setUp() {
        repository = new UserInMemoryRepository(new ArrayList<>());
        service = new UserServiceImpl(repository);
    }

    @Test
    public void saveUser() {
        User user = createUser();
        user = service.saveUser(user);

        assertNotNull(user);

        List<User> users = repository.findAll();
        assertFalse(users.isEmpty());
        assertTrue(users.size() == 1);

        assertEquals(1, users.get(0).getId());
        assertEquals(user.getName(), users.get(0).getName());
        assertEquals(user.getLastName(), users.get(0).getLastName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveUserNull() {
        User user;
        user = service.saveUser(null);

        assertNull(user);
        assertTrue(repository.findAll().isEmpty());
    }

    @Test(expected = ValidationException.class)
    public void saveUserBadPassword() {
        User user = createUser();
        user.setPassword("2122");

        service.saveUser(user);
    }

    @Test(expected = ValidationException.class)
    public void saveBadPhone() {
        User user = createUser();
        user.setPhone("032842");

        service.saveUser(user);
    }

    @Test
    public void findUserById() {
        User user = createUser();
        user = repository.save(user);

        Optional<User> foundUser = service.findUserById(1);
        System.out.println(foundUser);

        assertTrue(foundUser.isPresent());
        assertEquals(user.getId(), foundUser.get().getId());
    }

    @Test
    public void notFoundUserById() {
        Optional<User> foundUser = service.findUserById(1_000_000L);
        System.out.println(foundUser);

        assertFalse(foundUser.isPresent());
    }

    @Test
    public void findAllUsers() {
        repository.save(createUser());
        repository.save(createUser());

        List<User> users = service.findAllUsers();
        System.out.println(users);

        assertFalse(users.isEmpty());
        assertTrue(users.size() == 2);
    }
}