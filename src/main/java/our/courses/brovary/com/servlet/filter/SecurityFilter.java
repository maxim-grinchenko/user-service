package our.courses.brovary.com.servlet.filter;

import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import our.courses.brovary.com.config.AppConfig;
import our.courses.brovary.com.infra.exception.UnauthorizedException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class SecurityFilter implements Filter {
    private static final String AUTHORIZATION = "Authorization";
    private AppConfig config;
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("Init security filter");
        this.config = ConfigFactory.create(AppConfig.class);
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String basic = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(basic)) throw new UnauthorizedException();

        String credentials = extractBasic(basic).trim();
        if (!Base64.isBase64(credentials)) throw new UnauthorizedException();

        credentials = new String(Base64.decodeBase64(credentials));

        String[] arr = credentials.split(":");
        if (arr.length < 2 || !checkCredentials(arr[0], arr[1])) throw new UnauthorizedException();

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private static String extractBasic(String string) {
        return string.replaceFirst("[B|b][A|a][S|s][I|i][C|c]", "");
    }

    private boolean checkCredentials(String login, String password) {
        LOGGER.info("Login: {}, password: {}", login, password);
        return config.securityLogin().equals(login) && config.securityPassword().equals(password);
    }
}