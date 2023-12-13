package ecommerce.project.backend.controllers;

import ecommerce.project.backend.dto.CommentDTO;
import ecommerce.project.backend.entities.Comment;
import ecommerce.project.backend.requests.CommentRequest;
import ecommerce.project.backend.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Tag(name = "Comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    @Operation(summary = "Create comment for a post")
    public ResponseEntity<Object> createComment(@RequestParam Long postId, @RequestBody @Valid CommentRequest commentRequest) {
        CommentDTO newComment = commentService.createComment(postId, commentRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("newComment", newComment);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/update/{commentId}")
    @Operation(summary = "Update comment for a post")
    public ResponseEntity<Object> updateComment(@PathVariable Long commentId, @RequestBody @Valid CommentRequest commentRequest) {
        CommentDTO updatedComment = commentService.updateComment(commentId, commentRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("updatedComment", updatedComment);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{commentId}")
    @Operation(summary = "Delete comment for a post")
    public ResponseEntity<Object> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("msg", "Delete comment successfully!");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
