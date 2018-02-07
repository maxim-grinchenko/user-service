package our.courses.brovary.com.servlet.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class EncodingFilter implements javax.servlet.Filter {
    private static final String ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";

    @Override
    public void init(FilterConfig fConfig) {
        LOGGER.info("Init encoding filter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);

        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}