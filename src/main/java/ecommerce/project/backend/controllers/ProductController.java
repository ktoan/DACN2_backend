package ecommerce.project.backend.controllers;

import ecommerce.project.backend.dto.ProductDTO;
import ecommerce.project.backend.requests.ProductRequest;
import ecommerce.project.backend.responses.PagingResponse;
import ecommerce.project.backend.services.ProductService;
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
@RequestMapping("/api/v1/products")
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("")
    @Operation(summary = "Fetch all products")
    public ResponseEntity<Object> fetchAllProduct(
            @RequestParam(name = "nameLike", defaultValue = "") String s,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "desc") String sortDir,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "limit", required = false) Integer limit) {
        if (page == null || limit == null) {
            List<ProductDTO> list = productService.fetchAllProducts(s, sortBy, sortDir);
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", true);
            resp.put("products", list);
            return ResponseEntity.ok(resp);
        } else {
            PagingResponse pagingResponse = productService.fetchProductsByPaging(s, limit, page, sortBy, sortDir);
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", true);
            resp.put("data", pagingResponse);
            return ResponseEntity.ok(resp);
        }
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by its own id")
    public ResponseEntity<Object> getProductById(@PathVariable Long productId) {
        ProductDTO product = productService.getProductById(productId);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("product", product);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create new product")
    public ResponseEntity<Object> createProduct(@ModelAttribute @Valid ProductRequest productRequest) {
        ProductDTO newProduct = productService.createProduct(productRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("newProduct", newProduct);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update product's information")
    public ResponseEntity<Object> updateProduct(@PathVariable Long productId, @ModelAttribute @Valid ProductRequest productRequest) {
        ProductDTO updatedProduct = productService.updateProduct(productId, productRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("updatedProduct", updatedProduct);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    @Operation(summary = "Delete a product by its own id")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("msg", "Delete product successfully!");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
