package ecommerce.project.backend.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PagingResponse {
    private Object content;
    private Integer page;
    private Integer limit;
    private Integer totalPages;
    private Long totalElement;
}
