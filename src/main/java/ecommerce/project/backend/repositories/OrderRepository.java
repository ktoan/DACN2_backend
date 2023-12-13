package ecommerce.project.backend.repositories;

import ecommerce.project.backend.entities.Order;
import ecommerce.project.backend.entities.Product;
import ecommerce.project.backend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUser(User user, Pageable pageable);
    List<Order> findAllByUser(User user);
}
