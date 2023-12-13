package ecommerce.project.backend.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Column(nullable = false)
    private String text;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> childList = new HashSet<>();

    public void removeChild(Long childId) {
        this.childList.removeIf(child -> Objects.equals(child.getId(), childId));
    }
}
