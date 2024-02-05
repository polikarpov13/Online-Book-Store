package book.store.onlinebookstore.security;

import book.store.onlinebookstore.dto.user.UserLoginRequestDto;
import book.store.onlinebookstore.dto.user.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestDto.email(), userLoginRequestDto.password())
        );
        String token = jwtUtil.generateToken(userLoginRequestDto.email());
        return new UserLoginResponseDto(token);
    }
}
