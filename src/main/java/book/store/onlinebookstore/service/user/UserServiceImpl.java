package book.store.onlinebookstore.service.user;

import book.store.onlinebookstore.dto.user.RegisterUserRequestDto;
import book.store.onlinebookstore.dto.user.UserResponseDto;
import book.store.onlinebookstore.exception.RegistrationException;
import book.store.onlinebookstore.mapper.UserMapper;
import book.store.onlinebookstore.model.User;
import book.store.onlinebookstore.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(RegisterUserRequestDto registerUserRequestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(registerUserRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User with email "
                    + registerUserRequestDto.getEmail() + " already exists!");
        }
        User user = new User();
        user.setEmail(registerUserRequestDto.getEmail());
        user.setFirstName(registerUserRequestDto.getFirstName());
        user.setLastName(registerUserRequestDto.getLastName());
        user.setShippingAddress(registerUserRequestDto.getShippingAddress());
        user.setPassword(passwordEncoder.encode(registerUserRequestDto.getPassword()));

        return userMapper.toResponseDto(userRepository.save(user));
    }
}
