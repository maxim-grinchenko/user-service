package our.courses.brovary.com.infra.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import our.courses.brovary.com.domain.dto.UserDTO;
import our.courses.brovary.com.domain.entity.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    User userDTOtoUser(UserDTO userDTO);
}