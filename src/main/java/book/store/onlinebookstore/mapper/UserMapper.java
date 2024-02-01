package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.user.UserResponseDto;
import book.store.onlinebookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toResponseDto(User user);
}
