package ecommerce.project.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ecommerce.project.backend.entities.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProductDTO extends BaseDTO {
    private String name;
    private String shortDescription;
    private String information;
    private Long amount;
    private Set<Image> images;
    private Double saleOff;
    private Set<CategoryDTO> categories;
    private Double price;
    private Set<ReviewDTO> reviews;
}
