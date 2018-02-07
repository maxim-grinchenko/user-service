package our.courses.brovary.com.servlet.base;

import lombok.extern.slf4j.Slf4j;
import our.courses.brovary.com.infra.exception.ValidationException;
import our.courses.brovary.com.servlet.ServletResponseWrapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public abstract class AbstractGetByIdServlet<E> extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (request.getPathInfo() == null) throw new ValidationException("Bad request param!");

        E result = processRequest(request.getPathInfo().replaceFirst("/", ""));
        ServletResponseWrapper rw = new ServletResponseWrapper(response);
        if (result != null) rw.setBody(result);
    }

    protected abstract E processRequest(String value);
}