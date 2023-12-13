package ecommerce.project.backend.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "images")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Image extends BaseEntity {
    @Column(nullable = false)
    private String url;
}
