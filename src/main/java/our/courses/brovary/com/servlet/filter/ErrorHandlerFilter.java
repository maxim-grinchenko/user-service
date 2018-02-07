package our.courses.brovary.com.servlet.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import our.courses.brovary.com.domain.error.ErrorInfo;
import our.courses.brovary.com.infra.exception.UnauthorizedException;
import our.courses.brovary.com.infra.exception.ValidationException;
import our.courses.brovary.com.infra.exception.base.AppException;
import our.courses.brovary.com.singleton.MapperFactory;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class ErrorHandlerFilter implements javax.servlet.Filter {
    private static final int BAD_REQUEST_CODE = 400;
    private static final int UNAUTHORIZED_CODE = 401;
    private static final int INTERNAL_SERVER_ERROR_CODE = 500;

    private ObjectMapper mapper;

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("Init error handler filter");
        mapper = MapperFactory.INSTANCE.getMapper();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);

            ErrorInfo info = new ErrorInfo();
            info.setStatus(handleStatus(ex));
            info.setPath(request.getServletPath());
            info.setException(ex.getClass().getName());
            info.setTime(new Date().getTime());
            info.setMessage(handleMessage(ex));

            setErrorResponse(response, info);
        }
    }

    @Override
    public void destroy() {

    }

    private int handleStatus(Exception ex) {
        if (ex instanceof ValidationException) return BAD_REQUEST_CODE;
        if (ex instanceof UnauthorizedException) return UNAUTHORIZED_CODE;
        return INTERNAL_SERVER_ERROR_CODE;
    }

    private String handleMessage(Exception ex) {
        if (ex instanceof AppException) return ex.getMessage();
        return getStackTrace(ex);
    }

    private void setErrorResponse(HttpServletResponse response, ErrorInfo info) {
        try {
            String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(info);

            response.setContentType("application/json charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(info.getStatus());
            response.getWriter().write(result);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }
    }
}