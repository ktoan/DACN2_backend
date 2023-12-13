package ecommerce.project.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CommentDTO extends BaseDTO {
    private String text;
    private UserDTO user;
    private Set<CommentDTO> childList;
}
