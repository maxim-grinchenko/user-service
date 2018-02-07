package our.courses.brovary.com.infra.util;

import our.courses.brovary.com.infra.exception.ValidationException;

public class Validate {
    public static <T> T notNull(T object, String message, Object... values) {
        if (object == null) {
            throw new ValidationException(String.format(message, values));
        } else {
            return object;
        }
    }
}