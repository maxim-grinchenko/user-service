package our.courses.brovary.com.repository.impl;

import our.courses.brovary.com.domain.entity.User;
import our.courses.brovary.com.repository.UserRepository;

import java.util.Collections;
import java.util.List;

public class UserInMemoryRepository implements UserRepository {
    private long count;
    private final List<User> users;

    public UserInMemoryRepository(List<User> users) {
        this.users = users;
    }

    @Override
    public void deleteById(final long id) {
        users.removeIf(std -> std.getId() == id);
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    @Override
    public boolean existsById(final long id) {
        return users.stream().anyMatch(std -> std.getId() == id);
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public User findById(final long id) {
        return users.stream().filter(std -> std.getId() == id).findFirst().orElse(null);
    }

    @Override
    public User save(final User user) {
        if (user == null) return null;
        users.add(user);
        user.setId(++count);
        return user;
    }

    @Override
    public void saveAll(List<User> users) {
        users.forEach(this::save);
    }
}