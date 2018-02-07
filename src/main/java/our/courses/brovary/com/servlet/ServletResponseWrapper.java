package our.courses.brovary.com.servlet;

import lombok.NonNull;
import our.courses.brovary.com.singleton.MapperFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletResponseWrapper {
    private HttpServletResponse response;

    public ServletResponseWrapper(@NonNull HttpServletResponse response) {
        this.response = response;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    public <T> void setBody(@NonNull T result) {
        try {
            String body = MapperFactory.INSTANCE.getMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(result);
            response.getWriter().write(body);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}