package ecommerce.project.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ecommerce.project.backend.enums.OrderStatus;
import ecommerce.project.backend.enums.PaymentMethod;
import ecommerce.project.backend.enums.PaymentStatus;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItems = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.CONFIRMING;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod = PaymentMethod.SHIP_CODE;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;


    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }
}
