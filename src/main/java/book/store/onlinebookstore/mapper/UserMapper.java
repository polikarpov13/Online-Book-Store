package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.user.UserResponseDto;
import book.store.onlinebookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface UserMapper {
    UserResponseDto toResponseDto(User user);

    default User map(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    default Long map(User user) {
        return user.getId();
    }
}
