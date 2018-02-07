package our.courses.brovary.com.domain.error;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ErrorInfo {
    private int status;
    private String path;
    private String exception;
    private String message;
    private long time;
}