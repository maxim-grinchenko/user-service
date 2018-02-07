package our.courses.brovary.com.infra.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class DateUtil {
    public static LocalDateTime timeToLocalDateTime(long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), TimeZone.getDefault().toZoneId());
    }
}