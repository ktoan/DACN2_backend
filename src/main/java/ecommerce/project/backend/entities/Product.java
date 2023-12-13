package ecommerce.project.backend.entities;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String shortDescription;
    @Column(nullable = false, columnDefinition = "text")
    private String information;
    @Column(nullable = false)
    private Long amount;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "product_image", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> images = new HashSet<>();
    @Column(nullable = false)
    private Double saleOff;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();
    @Column(nullable = false)
    private Double price;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Where(clause = "parent_id is null")
    private Set<Review> reviews = new HashSet<>();

    public void addImage(Image image) {
        this.images.add(image);
    }

    public void removeImage(Long imageId) {
        this.images.removeIf(image -> Objects.equals(image.getId(), imageId));
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Long categoryId) {
        this.categories.removeIf(category -> Objects.equals(category.getId(), categoryId));
    }

    public void removeReview(Long reviewId) {
        this.reviews.removeIf(review -> Objects.equals(review.getId(), reviewId));
    }
}
