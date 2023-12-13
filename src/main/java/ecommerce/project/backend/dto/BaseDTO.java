package ecommerce.project.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseDTO {
    private Long id;
    private Date modifiedDate;
    private Date createdDate;
}
