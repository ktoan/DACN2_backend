package ecommerce.project.backend.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryRequest {
    @NotNull(message = "Category code can't be null!")
    @NotBlank(message = "Category code can't be blank!")
    private String code;
    @NotNull(message = "Category name can't be null!")
    @NotBlank(message = "Category name can't be blank!")
    private String name;
    @NotNull(message = "Category image can't be null!")
    private MultipartFile image;
}
