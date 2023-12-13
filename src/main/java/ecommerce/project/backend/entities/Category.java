package ecommerce.project.backend.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {
    @Column(nullable = false, unique = true, length = 100)
    private String code;
    @Column(nullable = false, length = 100)
    private String name;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;
}
