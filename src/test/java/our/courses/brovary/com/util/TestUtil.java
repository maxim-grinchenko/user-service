package our.courses.brovary.com.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Ignore;
import org.junit.Test;
import our.courses.brovary.com.domain.entity.User;

public class TestUtil {
    public static User createUser() {
        User user = new User();
        user.setName(RandomStringUtils.randomAlphabetic(7));
        user.setLastName(RandomStringUtils.randomAlphabetic(7));
        user.setMiddleName(RandomStringUtils.randomAlphabetic(7));
        user.setPhone("38093" + RandomStringUtils.randomNumeric(7));
        user.setEmail(RandomStringUtils.randomAlphabetic(7) + "@gmail.com");
        user.setLogin(RandomStringUtils.randomAlphabetic(7));
        user.setPassword(RandomStringUtils.randomAlphabetic(7) + "#" + RandomStringUtils.randomNumeric(4));
        user.setRoleId(1);
        return user;
    }

    @Ignore
    @Test
    public void createUserJson() throws JsonProcessingException {
        User user = createUser();

        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(user));
    }
}