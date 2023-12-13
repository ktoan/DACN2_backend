package ecommerce.project.backend.mappers;

import ecommerce.project.backend.dto.CommentDTO;
import ecommerce.project.backend.entities.Comment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final ModelMapper modelMapper;

    public Comment toEntity(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }

    public CommentDTO toDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }
}
