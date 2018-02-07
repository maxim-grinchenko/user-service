package our.courses.brovary.com.service;


import our.courses.brovary.com.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findUserById(long id);

    User saveUser(User student);

    List<User> findAllUsers();

    void updateUser(User student);
}