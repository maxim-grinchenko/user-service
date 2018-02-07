package our.courses.brovary.com.infra.transformer;

import org.junit.Before;
import org.junit.Test;
import our.courses.brovary.com.domain.dto.UserDTO;
import our.courses.brovary.com.domain.entity.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserMapperTest {
    private UserMapper mapper;

    @Before
    public void setUp() {
        mapper = UserMapper.INSTANCE;
    }

    @Test
    public void userToUserDTO() {
        User user = new User();
        user.setId(1L);
        user.setName("some name");
        user.setLastName("some last name");
        user.setMiddleName("some middle name");
        user.setLogin("some login");
        user.setLogin("some password");

        UserDTO userDTO = mapper.userToUserDTO(user);
        System.out.println(userDTO);

        assertNotNull(userDTO);
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getMiddleName(), userDTO.getMiddleName());
    }

    @Test
    public void userDTOtoUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("some name");
        userDTO.setLastName("some last name");
        userDTO.setMiddleName("some middle name");

        User user = mapper.userDTOtoUser(userDTO);
        System.out.println(userDTO);

        assertNotNull(user);
        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getMiddleName(), user.getMiddleName());
    }
}