package our.courses.brovary.com.domain.entity;

import lombok.*;
import net.sf.oval.constraint.*;
import our.courses.brovary.com.domain.entity.base.AbstractEntity;

import static our.courses.brovary.com.infra.util.RegexUtil.*;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity {
    @NotNull
    @NotEmpty
    @Length(min = 1, max = 30)
    private String name;
    @NotNull
    @NotEmpty
    @Length(min = 1, max = 30)
    private String lastName;
    @NotNull
    @NotEmpty
    @Length(min = 1, max = 30)
    private String middleName;
    @NotNull
    @NotEmpty
    @MatchPattern(pattern = PHONE_PATTERN)
    private String phone;
    @NotNull
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @NotEmpty
    @MatchPattern(pattern = LOGIN_PATTERN)
    private String login;
    @NotNull
    @NotEmpty
    @MatchPattern(pattern = PASSWORD_PATTERN)
    private String password;
    private int roleId;
}