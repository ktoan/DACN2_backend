package ecommerce.project.backend.services.impl;

import com.stripe.model.Charge;
import ecommerce.project.backend.dto.OrderDTO;
import ecommerce.project.backend.entities.*;
import ecommerce.project.backend.enums.PaymentMethod;
import ecommerce.project.backend.enums.PaymentStatus;
import ecommerce.project.backend.exceptions.ServerErrorException;
import ecommerce.project.backend.mappers.OrderMapper;
import ecommerce.project.backend.payment.stripe.PaymentService;
import ecommerce.project.backend.payment.stripe.StripeCustomerInformation;
import ecommerce.project.backend.repositories.OrderItemRepository;
import ecommerce.project.backend.repositories.OrderRepository;
import ecommerce.project.backend.requests.OrderItemRequest;
import ecommerce.project.backend.requests.OrderRequest;
import ecommerce.project.backend.responses.PagingResponse;
import ecommerce.project.backend.services.*;
import ecommerce.project.backend.utils.context.ContextService;
import ecommerce.project.backend.utils.paging.SortUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ContextService contextService;
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final PaymentService stripePaymentService;
    private final ProductService productService;
    private final AddressService addressService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final SortUtils sortUtils;
    private final CartService cartService;



    @Override
    @Transactional
    public OrderDTO createOrder(OrderRequest orderRequest) {
        try {
            Order order = new Order();
            User user = contextService.loadUserFromContext();
            if (user.getStripeCustomerId() == null) {
                StripeCustomerInformation customerInformation = new StripeCustomerInformation(user.getFullName(), user.getEmail());
                String stripeCustomerId = stripePaymentService.createCustomer(customerInformation);
                user.setStripeCustomerId(stripeCustomerId);
                userService.saveUser(user);
            }
            order.setUser(user);
            Address address = addressService.findAddressById(orderRequest.getAddressId());
            order.setAddress(address);
            order.setPaymentMethod(orderRequest.getPaymentMethod());
            order.setPaymentStatus(PaymentStatus.NOT_YET);
            order = saveOrder(order);
            long totalPrice = 0;
            for (OrderItemRequest orderItemRequest : orderRequest.getItems()) {
                Product product = productService.findProductById(orderItemRequest.getProductId());
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setQuantity(orderItemRequest.getQuantity());
                orderItem = saveOrderItem(orderItem);
                order.addOrderItem(orderItem);
                totalPrice += (product.getPrice() - product.getPrice() * product.getSaleOff() / 100) * orderItemRequest.getQuantity();
            }
            if (orderRequest.getPaymentMethod() == PaymentMethod.CARD) {
                String token = stripePaymentService.createCardToken(orderRequest.getStripeRequest());
                Map<String, Object> chargeParams = new HashMap<>();
                chargeParams.put("amount", totalPrice * 100);
                chargeParams.put("currency", "USD");
                chargeParams.put("source", token);
                chargeParams.put("description", orderRequest.getDescription());
                Charge.create(chargeParams);
                order.setPaymentStatus(PaymentStatus.HAVE_DONE);
            }
            order = saveOrder(order);
            for (OrderItem orderItem : order.getOrderItems()) {
                Cart cart = cartService.findCartByUserAndProduct(user, orderItem.getProduct());
                if (cart != null) {
                    cartService.deleteCartItem(cart.getId());
                }
            }
            return orderMapper.toDTO(order);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServerErrorException(e.getMessage());
        }
    }

    @Override
    public PagingResponse fetchUserOrders(Integer page, Integer limit) {
        User user = contextService.loadUserFromContext();
        Sort sort = sortUtils.getSort("id", "desc");
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Order> orderPage = orderRepository.findAllByUser(user, pageable);
        return new PagingResponse(orderPage.getContent(), orderPage.getNumber(), orderPage.getSize(), orderPage.getTotalPages(), orderPage.getTotalElements());
    }

    @Override
    public List<OrderDTO> fetchAllOrders() {
        User user = contextService.loadUserFromContext();
        List<Order> orders = orderRepository.findAllByUser(user);
        return orders.stream().map(orderMapper::toDTO).collect(Collectors.toList());
    }

    private Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    private OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
