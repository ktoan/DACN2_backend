package ecommerce.project.backend.services.impl;

import ecommerce.project.backend.dto.CommentDTO;
import ecommerce.project.backend.entities.Comment;
import ecommerce.project.backend.entities.Post;
import ecommerce.project.backend.entities.User;
import ecommerce.project.backend.exceptions.NotAccessException;
import ecommerce.project.backend.exceptions.NotFoundException;
import ecommerce.project.backend.mappers.CommentMapper;
import ecommerce.project.backend.repositories.CommentRepository;
import ecommerce.project.backend.requests.CommentRequest;
import ecommerce.project.backend.services.CommentService;
import ecommerce.project.backend.services.PostService;
import ecommerce.project.backend.utils.context.ContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static ecommerce.project.backend.constants.Messaging.COMMENT_NOT_FOUND_ID_MSG;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final ContextService contextService;

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException(String.format(COMMENT_NOT_FOUND_ID_MSG, commentId)));
    }

    @Override
    public CommentDTO createComment(Long postId, CommentRequest commentRequest) {
        Post post = postService.findPostById(postId);
        User user = contextService.loadUserFromContext();
        Comment comment = new Comment();
        comment.setText(commentRequest.getText());
        comment.setPost(post);
        comment.setUser(user);
        if (commentRequest.getParentId() != null) {
            Comment parent = findCommentById(commentRequest.getParentId());
            comment.setParent(parent);
        }
        comment = saveComment(comment);
        return commentMapper.toDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long commentId, CommentRequest commentRequest) {
        Comment comment = findCommentById(commentId);
        if (!Objects.equals(comment.getUser().getId(), contextService.loadUserFromContext().getId())) {
            throw new NotAccessException();
        }
        comment.setText(commentRequest.getText());
        comment = saveComment(comment);
        return commentMapper.toDTO(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = findCommentById(commentId);
        if (!Objects.equals(comment.getUser().getId(), contextService.loadUserFromContext().getId())) {
            throw new NotAccessException();
        }
        Post post = comment.getPost();
        post.removeComment(commentId);
        postService.savePost(post);
        Comment parent = comment.getParent();
        if (parent != null) {
            parent.removeChild(commentId);
            saveComment(parent);
        }
        commentRepository.delete(comment);
    }
}
