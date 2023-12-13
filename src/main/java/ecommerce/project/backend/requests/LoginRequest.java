package ecommerce.project.backend.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotNull(message = "Email can't be null!")
    @NotBlank(message = "Email can't be blank!")
    private String email;
    @NotNull(message = "Password can't be null!")
    @NotBlank(message = "Password can't be blank!")
    private String password;
}
