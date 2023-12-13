package ecommerce.project.backend.services;

import ecommerce.project.backend.dto.CommentDTO;
import ecommerce.project.backend.entities.Comment;
import ecommerce.project.backend.requests.CommentRequest;

public interface CommentService {
    Comment saveComment(Comment comment);
    Comment findCommentById(Long commentId);
    CommentDTO createComment(Long postId, CommentRequest commentRequest);
    CommentDTO updateComment(Long commentId, CommentRequest commentRequest);
    void deleteComment(Long commentId);
}
