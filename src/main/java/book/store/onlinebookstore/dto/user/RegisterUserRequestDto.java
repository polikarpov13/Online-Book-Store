package book.store.onlinebookstore.dto.user;

import book.store.onlinebookstore.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(firstString = "password", secondString = "repeatPassword")
public class RegisterUserRequestDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String shippingAddress;

    @NotBlank
    @Length(min = 8, max = 30)
    private String password;

    @NotBlank
    @Length(min = 8, max = 30)
    private String repeatPassword;
}
