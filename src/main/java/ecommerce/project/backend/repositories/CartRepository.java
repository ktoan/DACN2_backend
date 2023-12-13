package ecommerce.project.backend.repositories;

import ecommerce.project.backend.entities.Cart;
import ecommerce.project.backend.entities.Product;
import ecommerce.project.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByProductAndUser(Product product, User user);
    void deleteById(Long id);
}
