package ecommerce.project.backend.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductRequest {
    @NotNull(message = "Product name can't be null!")
    @NotBlank(message = "Product name can't be blank!")
    private String name;
    @NotNull(message = "Product short description can't be null!")
    @NotBlank(message = "Product short description can't be blank!")
    private String shortDescription;
    @NotNull(message = "Product information can't be null!")
    @NotBlank(message = "Product information can't be blank!")
    private String information;
    @NotNull(message = "Product amount can't be null!")
    private Long amount;
    @NotNull(message = "Product images can't be null!")
    private MultipartFile[] images;
    @NotNull(message = "Product category can't be null!")
    private String[] categoryListCode;
    @NotNull(message = "Product price can't be null!")
    private Double price;
    private Double saleOff = 0.0;
}
