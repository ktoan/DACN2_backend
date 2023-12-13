package ecommerce.project.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String shortDescription;
    @Column(columnDefinition = "text", nullable = false)
    private String content;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Where(clause = "parent_id is null")
    private Set<Comment> comments = new HashSet<>();

    public void removeComment(Long commentId) {
        this.comments.removeIf(comment -> Objects.equals(comment.getId(), commentId));
    }
}
