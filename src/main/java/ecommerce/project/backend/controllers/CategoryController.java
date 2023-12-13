package ecommerce.project.backend.controllers;

import ecommerce.project.backend.dto.CategoryDTO;
import ecommerce.project.backend.requests.CategoryRequest;
import ecommerce.project.backend.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Tag(name = "Category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    @Operation(summary = "Fetch all categories")
    public ResponseEntity<Object> fetchAllCategories() {
        List<CategoryDTO> list = categoryService.fetchAllCategories();
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("categories", list);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create new category")
    public ResponseEntity<Object> createCategory(@ModelAttribute @Valid CategoryRequest categoryRequest) {
        CategoryDTO newCategory = categoryService.createCategory(categoryRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("newCategory", newCategory);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}")
    @Operation(summary = "Update category's information")
    public ResponseEntity<Object> updateCategory(@PathVariable Long categoryId, @ModelAttribute @Valid CategoryRequest categoryRequest) {
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("updatedCategory", updatedCategory);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
