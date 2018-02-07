package our.courses.brovary.com.servlet.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import our.courses.brovary.com.infra.exception.ValidationException;
import our.courses.brovary.com.servlet.ServletResponseWrapper;
import our.courses.brovary.com.singleton.MapperFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;

@Slf4j
public abstract class AbstractPostServlet<Res, Req> extends HttpServlet {
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String body = getBody(request);

        Req req = null;
        if (!StringUtils.isBlank(body)) {
            Class<Req> clazz = (Class<Req>) ((ParameterizedType) this.getClass()
                    .getGenericSuperclass())
                    .getActualTypeArguments()[1];

            req = read(body, clazz);
        }
        Res result = processRequest(req);

        ServletResponseWrapper rw = new ServletResponseWrapper(response);
        if (result != null) rw.setBody(result);
    }

    private Req read(String json, Class<Req> clazz) {
        LOGGER.info("Class: {}", clazz);
        try {
            return MapperFactory.INSTANCE.getMapper().readValue(json, clazz);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ValidationException("Invalid json");
        }
    }

    private static String getBody(HttpServletRequest request) {
        try (InputStream is = request.getInputStream()) {
            return IOUtils.toString(is);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    protected abstract Res processRequest(Req value);
}