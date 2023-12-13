package ecommerce.project.backend.services;

import ecommerce.project.backend.dto.OrderDTO;
import ecommerce.project.backend.requests.OrderRequest;
import ecommerce.project.backend.responses.PagingResponse;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderRequest orderRequest) throws Exception;
    PagingResponse fetchUserOrders(Integer page, Integer limit);
    List<OrderDTO> fetchAllOrders();
}
