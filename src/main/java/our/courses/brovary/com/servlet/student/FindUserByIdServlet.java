package our.courses.brovary.com.servlet.student;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.LongValidator;
import our.courses.brovary.com.domain.dto.UserDTO;
import our.courses.brovary.com.infra.exception.ValidationException;
import our.courses.brovary.com.infra.transformer.UserMapper;
import our.courses.brovary.com.repository.UserRepository;
import our.courses.brovary.com.service.UserService;
import our.courses.brovary.com.service.impl.UserServiceImpl;
import our.courses.brovary.com.servlet.base.AbstractGetByIdServlet;
import our.courses.brovary.com.singleton.UserRepositoryFactory;

import javax.servlet.annotation.WebServlet;

@Slf4j
@WebServlet(urlPatterns = "/find/user/by/id/*")
public class FindUserByIdServlet extends AbstractGetByIdServlet<UserDTO> {
    private UserService service;
    private UserMapper mapper;

    @Override
    public void init() {
        UserRepository repository = UserRepositoryFactory.INSTANCE.getRepository();
        service = new UserServiceImpl(repository);
        mapper = UserMapper.INSTANCE;
    }

    @Override
    protected UserDTO processRequest(final String value) {
        Long id = LongValidator.getInstance().validate(value);
        if (id == null) throw new ValidationException("Not valid long!");
        return service.findUserById(id).map(mapper::userToUserDTO).orElseGet(UserDTO::new);
    }
}