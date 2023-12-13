package ecommerce.project.backend.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentRequest {
    @NotNull(message =  "Comment content can't be null!")
    @NotBlank(message =  "Comment content can't be blank!")
    private String text;
    private Long parentId;
}
