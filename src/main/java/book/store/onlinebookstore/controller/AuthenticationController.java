package book.store.onlinebookstore.controller;

import book.store.onlinebookstore.dto.user.RegisterUserRequestDto;
import book.store.onlinebookstore.dto.user.UserLoginRequestDto;
import book.store.onlinebookstore.dto.user.UserLoginResponseDto;
import book.store.onlinebookstore.dto.user.UserResponseDto;
import book.store.onlinebookstore.exception.RegistrationException;
import book.store.onlinebookstore.security.AuthenticationService;
import book.store.onlinebookstore.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public UserResponseDto registerUser(
            @RequestBody @Valid RegisterUserRequestDto registerUserRequestDto)
            throws RegistrationException {
        return userService.register(registerUserRequestDto);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(
            @RequestBody @Valid UserLoginRequestDto loginUserRequestDto) {
        return authenticationService.authenticate(loginUserRequestDto);
    }
}
