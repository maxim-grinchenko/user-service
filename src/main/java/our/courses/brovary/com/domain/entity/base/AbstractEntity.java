package our.courses.brovary.com.domain.entity.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.oval.constraint.Min;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public abstract class AbstractEntity {
    @Min(value = 0, inclusive = false, profiles = "UPDATE")
    private long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}