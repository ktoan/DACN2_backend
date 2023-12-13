package ecommerce.project.backend.mappers;

import ecommerce.project.backend.dto.OrderDTO;
import ecommerce.project.backend.entities.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final ModelMapper modelMapper;

    public Order toEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }

    public OrderDTO toDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
}
