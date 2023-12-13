package ecommerce.project.backend.utils.paging;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtils {
    public Sort getSort(String sortBy, String sortDir) {
        return sortDir.equals("desc") || sortDir.equals("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    }
}
