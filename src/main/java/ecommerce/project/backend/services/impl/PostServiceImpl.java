package ecommerce.project.backend.services.impl;

import ecommerce.project.backend.dto.PostDTO;
import ecommerce.project.backend.entities.Image;
import ecommerce.project.backend.entities.Post;
import ecommerce.project.backend.entities.User;
import ecommerce.project.backend.exceptions.NotAccessException;
import ecommerce.project.backend.exceptions.NotFoundException;
import ecommerce.project.backend.mappers.PostMapper;
import ecommerce.project.backend.repositories.PostRepository;
import ecommerce.project.backend.requests.PostRequest;
import ecommerce.project.backend.responses.PagingResponse;
import ecommerce.project.backend.services.ImageService;
import ecommerce.project.backend.services.PostService;
import ecommerce.project.backend.services.UserService;
import ecommerce.project.backend.utils.context.ContextService;
import ecommerce.project.backend.utils.paging.SortUtils;
import ecommerce.project.backend.utils.upload.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ecommerce.project.backend.constants.Messaging.POST_NOT_FOUND_ID_MSG;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final ImageService imageService;
    private final FileService fileService;
    private final ContextService contextService;
    private final SortUtils sortUtils;
    private final UserService userService;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public PostDTO createPost(PostRequest postRequest) {
        String url = fileService.uploadImage(postRequest.getImage());
        Image image = new Image(url);
        image = imageService.saveImage(image);
        User user = contextService.loadUserFromContext();
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setShortDescription(postRequest.getShortDescription());
        post.setContent(postRequest.getContent());
        post.setImage(image);
        post.setAuthor(user);
        post = savePost(post);
        return postMapper.toDTO(post);
    }

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = findPostById(postId);
        return postMapper.toDTO(post);
    }

    @Override
    public PagingResponse fetchPostsByPaging(String s, Integer limit, Integer page, String sortBy, String sortDir) {
        Sort sort = sortUtils.getSort(sortBy, sortDir);
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Post> postPage = postRepository.findAllByTitleContaining(s, pageable);
        return new PagingResponse(postPage.getContent(), postPage.getNumber(), postPage.getSize(), postPage.getTotalPages(), postPage.getTotalElements());
    }

    @Override
    public List<PostDTO> fetchAllPosts(String s, String sortBy, String sortDir) {
        Sort sort = sortUtils.getSort(sortBy, sortDir);
        List<Post> posts = postRepository.findAllByTitleContaining(s, sort);
        return posts.stream().map(postMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NotFoundException(String.format(POST_NOT_FOUND_ID_MSG, postId)));
    }

    @Override
    public void deletePost(Long postId) {
        Post post = findPostById(postId);
        if (!Objects.equals(post.getAuthor().getId(), contextService.loadUserFromContext().getId())) {
            throw new NotAccessException();
        }
        User user = post.getAuthor();
        user.removePost(postId);
        userService.saveUser(user);
        postRepository.delete(post);
    }
}
