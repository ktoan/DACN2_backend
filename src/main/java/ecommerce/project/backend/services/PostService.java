package ecommerce.project.backend.services;

import ecommerce.project.backend.dto.PostDTO;
import ecommerce.project.backend.dto.ProductDTO;
import ecommerce.project.backend.entities.Post;
import ecommerce.project.backend.requests.PostRequest;
import ecommerce.project.backend.responses.PagingResponse;

import java.util.List;

public interface PostService {
    Post savePost(Post post);
    Post findPostById(Long postId);
    PostDTO createPost(PostRequest postRequest);
    PostDTO getPostById(Long postId);
    PagingResponse fetchPostsByPaging(String s, Integer limit, Integer page, String sortBy, String sortDir);
    List<PostDTO> fetchAllPosts(String s, String sortBy, String sortDir);
    void deletePost(Long postId);
}
