package ecommerce.project.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Review extends BaseEntity {
    private Double value;
    @Column(nullable = false)
    private String comment;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Review parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Review> childList = new HashSet<>();

    public void removeChild(Long childId) {
        this.childList.removeIf(child -> Objects.equals(child.getId(), childId));
    }
}
