package book.store.onlinebookstore.service.user;

import book.store.onlinebookstore.dto.user.RegisterUserRequestDto;
import book.store.onlinebookstore.dto.user.UserResponseDto;
import book.store.onlinebookstore.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(RegisterUserRequestDto registerUserRequestDto)
            throws RegistrationException;
}
