package our.courses.brovary.com.servlet.student;

import lombok.extern.slf4j.Slf4j;
import our.courses.brovary.com.infra.transformer.UserMapper;
import our.courses.brovary.com.repository.UserRepository;
import our.courses.brovary.com.service.UserService;
import our.courses.brovary.com.service.impl.UserServiceImpl;
import our.courses.brovary.com.servlet.ServletResponseWrapper;
import our.courses.brovary.com.singleton.UserRepositoryFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/find/all/users")
@Slf4j
public class FindAllUsersServlet extends HttpServlet {
    private UserService service;
    private UserMapper mapper;

    @Override
    public void init() {
        UserRepository studentRepository = UserRepositoryFactory.INSTANCE.getRepository();
        service = new UserServiceImpl(studentRepository);
        mapper = UserMapper.INSTANCE;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(response);
    }

    private void processRequest(HttpServletResponse response) {
        ServletResponseWrapper rw = new ServletResponseWrapper(response);
        rw.setBody(
                service.findAllUsers().stream()
                        .map(mapper::userToUserDTO)
                        .collect(Collectors.toList())
        );
    }
}