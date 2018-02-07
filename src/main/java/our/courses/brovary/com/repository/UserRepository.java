package our.courses.brovary.com.repository;


import our.courses.brovary.com.domain.entity.User;

import java.util.List;

public interface UserRepository {
    void deleteById(long id);

    void deleteAll();

    boolean existsById(long id);

    List<User> findAll();

    User findById(long id);

    User save(User user);

    void saveAll(List<User> users);
}