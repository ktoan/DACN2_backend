package ecommerce.project.backend.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address extends BaseEntity {
    private String state;
    private String country;
    private String street;
    private String district;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
