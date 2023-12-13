package ecommerce.project.backend.repositories;

import ecommerce.project.backend.entities.Cart;
import ecommerce.project.backend.entities.Category;
import ecommerce.project.backend.entities.Product;
import ecommerce.project.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCode(String code);
    Boolean existsByCode(String code);
}
