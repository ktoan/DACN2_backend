package ecommerce.project.backend.services.impl;

import ecommerce.project.backend.dto.CartDTO;
import ecommerce.project.backend.entities.Cart;
import ecommerce.project.backend.entities.Product;
import ecommerce.project.backend.entities.User;
import ecommerce.project.backend.exceptions.NotAccessException;
import ecommerce.project.backend.exceptions.NotFoundException;
import ecommerce.project.backend.mappers.CartMapper;
import ecommerce.project.backend.repositories.CartRepository;
import ecommerce.project.backend.requests.CartRequest;
import ecommerce.project.backend.services.CartService;
import ecommerce.project.backend.services.ProductService;
import ecommerce.project.backend.services.UserService;
import ecommerce.project.backend.utils.context.ContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static ecommerce.project.backend.constants.Messaging.CART_NOT_FOUND_ID_MSG;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final UserService userService;
    private final ContextService contextService;
    private final ProductService productService;

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public List<CartDTO> fetchCartOfSessionUser() {
        User user = contextService.loadUserFromContext();
         return user.getCart().stream().map(cartMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CartDTO updateCart(CartRequest cartRequest) {
        Cart cart;
        User user = contextService.loadUserFromContext();
        Product product = productService.findProductById(cartRequest.getProductId());
        if (user.getCart().stream().anyMatch(c -> Objects.equals(c.getProduct().getId(), cartRequest.getProductId()))) {
            cart = cartRepository.findByProductAndUser(product, user);
            long totalQuantity = cart.getQuantity() + cartRequest.getQuantity();
            cart.setQuantity(totalQuantity <= 0 ? 1 : totalQuantity);
        } else {
            cart = new Cart(product, cartRequest.getQuantity() < 1 ? 1 : cartRequest.getQuantity(), user);
        }
        cart = saveCart(cart);
        return cartMapper.toDTO(cart);
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        Cart cart = findCartById(cartItemId);
        if (!Objects.equals(cart.getUser().getId(), contextService.loadUserFromContext().getId())) {
            throw new NotAccessException();
        }
        cart.getUser().removeCartItem(cartItemId);
        userService.saveUser(cart.getUser());
        cartRepository.deleteById(cartItemId);
    }

    @Override
    public Cart findCartById(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException(String.format(CART_NOT_FOUND_ID_MSG, cartId)));
    }

    @Override
    public Cart findCartByUserAndProduct(User user, Product product) {
        return cartRepository.findByProductAndUser(product, user);
    }
}
