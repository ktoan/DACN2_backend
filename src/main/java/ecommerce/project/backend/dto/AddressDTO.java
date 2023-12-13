package ecommerce.project.backend.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressDTO extends BaseDTO {
    @NotNull(message = "State can't be null!")
    @NotBlank(message = "State can't be blank!")
    private String state;
    @NotNull(message = "Country can't be null!")
    @NotBlank(message = "Country can't be blank!")
    private String country;
    @NotNull(message = "Street can't be null!")
    @NotBlank(message = "Street can't be blank!")
    private String street;
    @NotNull(message = "District can't be null!")
    @NotBlank(message = "District can't be blank!")
    private String district;
}
