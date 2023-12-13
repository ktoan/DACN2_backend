package ecommerce.project.backend.controllers;

import ecommerce.project.backend.dto.CartDTO;
import ecommerce.project.backend.requests.CartRequest;
import ecommerce.project.backend.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/cart")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Tag(name = "Cart")
public class CartController {
    private final CartService cartService;

    @GetMapping("")
    @Operation(summary = "Fetch cart of a user")
    public ResponseEntity<Object> fetchCart() {
        List<CartDTO> cart = cartService.fetchCartOfSessionUser();
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("cart", cart);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Add/Update product to/in user's cart")
    public ResponseEntity<Object> updateCart(@RequestBody @Valid CartRequest cartRequest) {
        CartDTO cart = cartService.updateCart(cartRequest);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("cartItem", cart);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    @Operation(summary = "Remove cart item")
    public ResponseEntity<Object> deleteCartItem(@PathVariable Long cartItemId) {
        cartService.deleteCartItem(cartItemId);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("msg", "Remove cart item successfully!");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
