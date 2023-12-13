package ecommerce.project.backend.requests;

import ecommerce.project.backend.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@Getter
@Setter
public class UpdateUserRequest {
    @NotNull(message = "Full name can't be null!")
    @NotBlank(message = "Full name can't be blank!")
    private String fullName;
    @NotNull(message = "Email can't be null!")
    @NotBlank(message = "Email can't be blank!")
    @Email(message = "Email is invalid!")
    private String email;
    @NotNull(message = "Phone number can't be null!")
    @NotBlank(message = "Phone number can't be blank!")
    @Pattern(regexp = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$", message = "Phone is invalid!")
    private String phone;
    @NotNull(message = "Day of birth can't be null!")
    private Date dayOfBirth;
    @NotNull(message = "Gender can't be null!")
    private Gender gender;
}
