package ecommerce.project.backend.mappers;

import ecommerce.project.backend.dto.CartDTO;
import ecommerce.project.backend.entities.Cart;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartMapper {
    private final ModelMapper modelMapper;

    public Cart toEntity(CartDTO cartDTO) {
        return modelMapper.map(cartDTO, Cart.class);
    }

    public CartDTO toDTO(Cart cart) {
        return modelMapper.map(cart, CartDTO.class);
    }
}
