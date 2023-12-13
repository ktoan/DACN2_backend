package ecommerce.project.backend.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "confirmation_tokens")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String token;
    @Column(nullable = false)
    private Date expiredAt;
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
