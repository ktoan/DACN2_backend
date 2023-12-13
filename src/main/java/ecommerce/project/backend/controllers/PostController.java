package ecommerce.project.backend.controllers;

import ecommerce.project.backend.dto.PostDTO;
import ecommerce.project.backend.requests.PostRequest;
import ecommerce.project.backend.responses.PagingResponse;
import ecommerce.project.backend.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequiredArgsConstructor
@Tag(name = "Post")
public class PostController {
    private final PostService postService;

    @GetMapping("")
    @Operation(summary = "Fetch all posts")
    public ResponseEntity<Object> fetchAllPosts(
            @RequestParam(name = "titleLike", defaultValue = "") String s,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "desc") String sortDir,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "limit", required = false) Integer limit
    ) {
        if (page == null || limit == null) {
            List<PostDTO> list = postService.fetchAllPosts(s, sortBy, sortDir);
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", true);
            resp.put("posts", list);
            return ResponseEntity.ok(resp);
        } else {
            PagingResponse pagingResponse = postService.fetchPostsByPaging(s, limit, page, sortBy, sortDir);
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", true);
            resp.put("data", pagingResponse);
            return ResponseEntity.ok(resp);
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Create new post")
    public ResponseEntity<Object> createPost(@ModelAttribute @Valid PostRequest postRequest) {
        PostDTO newPost = postService.createPost(postRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("newPost", newPost);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Get post by its own id")
    public ResponseEntity<Object> getPostById(@PathVariable Long postId) {
        PostDTO post = postService.getPostById(postId);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("post", post);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    @Operation(summary = "Delete post")
    public ResponseEntity<Object> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("msg", "Delete post successfully!");
        return ResponseEntity.ok(resp);
    }
}
