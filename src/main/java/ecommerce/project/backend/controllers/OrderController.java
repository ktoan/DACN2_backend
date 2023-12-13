package ecommerce.project.backend.controllers;

import ecommerce.project.backend.dto.OrderDTO;
import ecommerce.project.backend.payment.stripe.PaymentService;
import ecommerce.project.backend.payment.stripe.StripeCustomerInformation;
import ecommerce.project.backend.requests.OrderRequest;
import ecommerce.project.backend.requests.StripeRequest;
import ecommerce.project.backend.responses.PagingResponse;
import ecommerce.project.backend.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Tag(name = "Order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create-order")
    @Operation(summary = "Create new order")
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequest orderRequest) throws Exception {
        OrderDTO newOrder =  orderService.createOrder(orderRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("newOrder", newOrder);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("")
    @Operation(summary = "Find all user own orders")
    public ResponseEntity<Object> findUserOrders(@RequestParam(name = "page", required = false) Integer page,
                                                 @RequestParam(name = "limit", required = false) Integer limit) {
        if (page != null && limit != null) {
            PagingResponse pagingResponse = orderService.fetchUserOrders(page, limit);
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", true);
            resp.put("data", pagingResponse);
            return ResponseEntity.ok(resp);
        } else {
            List<OrderDTO> orders = orderService.fetchAllOrders();
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", true);
            resp.put("orders", orders);
            return ResponseEntity.ok(resp);
        }
    }
}
