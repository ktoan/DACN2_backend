package ecommerce.project.backend.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChangePasswordRequest {
    @NotNull(message = "Old password can't be null!")
    @NotBlank(message = "Old password can't be blank!")
    private String oldPassword;
    @NotNull(message = "New password can't be null!")
    @NotBlank(message = "New password can't be blank!")
    @Size(min = 6, message = "New password at least 6!")
    private String newPassword;
}
