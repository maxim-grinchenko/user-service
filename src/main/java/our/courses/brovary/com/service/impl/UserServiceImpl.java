package our.courses.brovary.com.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import our.courses.brovary.com.domain.entity.User;
import our.courses.brovary.com.infra.exception.ValidationException;
import our.courses.brovary.com.repository.UserRepository;
import our.courses.brovary.com.service.UserService;
import our.courses.brovary.com.singleton.ValidationFactory;

import java.util.List;
import java.util.Optional;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findUserById(final long id) {
        LOGGER.info("Find user by id: {}", id);
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public User saveUser(final User user) {
        LOGGER.info("Save user: {}", user);

        Validator validator = ValidationFactory.getValidator();
        List<ConstraintViolation> violations = validator.validate(user);
        if (!violations.isEmpty())
            throw new ValidationException("User validation fail", violations);

        // user role
        user.setRoleId(2);
        return repository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        LOGGER.info("Start find all users");
        return repository.findAll();
    }

    @Override
    public void updateUser(final User user) {
        LOGGER.info("Update user: {}", user);

        Validator validator = ValidationFactory.getValidator();
        validator.enableProfile("UPDATE");

        List<ConstraintViolation> violations = validator.validate(user);
        if (!violations.isEmpty())
            throw new ValidationException("User validation fail", violations);
        repository.save(user);
    }
}