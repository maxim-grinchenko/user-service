package our.courses.brovary.com.infra.exception;

import our.courses.brovary.com.infra.exception.base.AppException;

public class UnauthorizedException extends AppException {
    private static final String UNAUTHORIZED = "unauthorized";

    public UnauthorizedException() {
        super(UNAUTHORIZED);
    }
}