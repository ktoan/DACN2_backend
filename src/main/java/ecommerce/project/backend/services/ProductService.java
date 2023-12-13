package ecommerce.project.backend.services;

import ecommerce.project.backend.dto.ProductDTO;
import ecommerce.project.backend.entities.Product;
import ecommerce.project.backend.requests.ProductRequest;
import ecommerce.project.backend.responses.PagingResponse;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    Product findProductById(Long productId);
    ProductDTO createProduct(ProductRequest productRequest);
    ProductDTO getProductById(Long productId);
    ProductDTO updateProduct(Long productId, ProductRequest productRequest);
    PagingResponse fetchProductsByPaging(String s, Integer limit, Integer page, String sortBy, String sortDir);
    List<ProductDTO> fetchAllProducts(String s, String sortBy, String sortDir);
    void deleteProduct(Long productId);
}
