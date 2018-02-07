package our.courses.brovary.com.servlet.student;

import lombok.extern.slf4j.Slf4j;
import our.courses.brovary.com.domain.entity.User;
import our.courses.brovary.com.infra.util.Validate;
import our.courses.brovary.com.repository.UserRepository;
import our.courses.brovary.com.service.UserService;
import our.courses.brovary.com.service.impl.UserServiceImpl;
import our.courses.brovary.com.servlet.base.AbstractPostServlet;
import our.courses.brovary.com.singleton.UserRepositoryFactory;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/create/user")
@Slf4j
public class CreateUserServlet extends AbstractPostServlet<User, User> {
    private UserService service;

    @Override
    public void init() {
        UserRepository repository = UserRepositoryFactory.INSTANCE.getRepository();
        service = new UserServiceImpl(repository);
    }

    @Override
    protected User processRequest(final User student) {
        return service.saveUser(Validate.notNull(student, "User cant be null!"));
    }
}