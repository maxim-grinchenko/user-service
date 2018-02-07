package our.courses.brovary.com.infra.exception.base;


public abstract class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }
}