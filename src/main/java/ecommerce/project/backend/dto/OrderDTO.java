package ecommerce.project.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ecommerce.project.backend.entities.Address;
import ecommerce.project.backend.entities.OrderItem;
import ecommerce.project.backend.enums.OrderStatus;
import ecommerce.project.backend.enums.PaymentMethod;
import ecommerce.project.backend.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OrderDTO extends BaseDTO {
    private AddressDTO address;
    private Set<OrderItemDTO> orderItems = new HashSet<>();
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
}
