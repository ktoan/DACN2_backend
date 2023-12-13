package ecommerce.project.backend.mappers;

import ecommerce.project.backend.dto.PostDTO;
import ecommerce.project.backend.entities.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {
    private final ModelMapper modelMapper;

    public Post toEntity(PostDTO postDTO) {
        return modelMapper.map(postDTO, Post.class);
    }

    public PostDTO toDTO(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }
}
