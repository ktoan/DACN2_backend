package ecommerce.project.backend.dto;

import ecommerce.project.backend.entities.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO extends BaseDTO {
    private String code;
    private String name;
    private Image image;
}
