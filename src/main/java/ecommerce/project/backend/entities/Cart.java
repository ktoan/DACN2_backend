package ecommerce.project.backend.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(nullable = false)
    private Long quantity;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
