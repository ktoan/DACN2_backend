package ecommerce.project.backend.mappers;

import ecommerce.project.backend.dto.UserDTO;
import ecommerce.project.backend.entities.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public User toEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
