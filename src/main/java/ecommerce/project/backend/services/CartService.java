package ecommerce.project.backend.services;

import ecommerce.project.backend.dto.CartDTO;
import ecommerce.project.backend.entities.Cart;
import ecommerce.project.backend.entities.Product;
import ecommerce.project.backend.entities.User;
import ecommerce.project.backend.requests.CartRequest;

import java.util.List;

public interface CartService {
    List<CartDTO> fetchCartOfSessionUser();
    Cart saveCart(Cart cart);
    Cart findCartById(Long cartId);
    Cart findCartByUserAndProduct(User user, Product product);
    CartDTO updateCart(CartRequest cartRequest);
    void deleteCartItem(Long cartItemId);
}
