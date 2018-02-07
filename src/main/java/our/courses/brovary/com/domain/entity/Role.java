package our.courses.brovary.com.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import our.courses.brovary.com.domain.entity.base.AbstractEntity;

@Getter
@Setter
@ToString
public class Role extends AbstractEntity {
    private String name;
}