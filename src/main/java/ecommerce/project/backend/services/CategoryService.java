package ecommerce.project.backend.services;

import ecommerce.project.backend.dto.CategoryDTO;
import ecommerce.project.backend.entities.Category;
import ecommerce.project.backend.requests.CategoryRequest;

import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    Category getCategoryByCode(String code);
    CategoryDTO createCategory(CategoryRequest categoryRequest);
    CategoryDTO updateCategory(Long categoryId, CategoryRequest categoryRequest);
    List<CategoryDTO> fetchAllCategories();
}
