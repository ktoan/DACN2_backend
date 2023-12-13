package ecommerce.project.backend.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PostRequest {
    @NotNull(message = "Post title can't be null!")
    @NotBlank(message = "Post title can't be blank!")
    private String title;
    @NotNull(message = "Post short description can't be null!")
    @NotBlank(message = "Post short description can't be blank!")
    private String shortDescription;
    @NotNull(message = "Post content can't be null!")
    @NotBlank(message = "Post content can't be blank!")
    private String content;
    @NotNull(message = "Post image can't be null!")
    private MultipartFile image;
}
