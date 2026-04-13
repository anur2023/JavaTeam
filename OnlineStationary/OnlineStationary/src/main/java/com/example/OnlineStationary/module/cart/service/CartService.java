package com.example.OnlineStationary.module.cart.service;

import com.example.OnlineStationary.module.cart.dto.CartItemRequest;
import com.example.OnlineStationary.module.cart.dto.CartResponse;
import com.example.OnlineStationary.module.cart.entity.Cart;
import com.example.OnlineStationary.module.cart.entity.CartItem;
import com.example.OnlineStationary.module.cart.repository.CartItemRepository;
import com.example.OnlineStationary.module.cart.repository.CartRepository;
import com.example.OnlineStationary.module.product.entity.Product;
import com.example.OnlineStationary.module.product.repository.ProductRepository;
import com.example.OnlineStationary.module.user.entity.UserProfile;
import com.example.OnlineStationary.module.user.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserProfileRepository userRepository;

    public CartResponse getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return buildCartResponse(cart);
    }

    public CartResponse addToCart(Long userId, CartItemRequest request) {
        UserProfile user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cartItemRepository
                .findByCartCartIdAndProductProductId(cart.getCartId(), product.getProductId());

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity());
            cartItemRepository.save(newItem);
        }

        return buildCartResponse(cartRepository.findByUserId(userId).get());
    }

    public CartResponse updateCartItem(Long cartItemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return buildCartResponse(item.getCart());
    }

    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    private CartResponse buildCartResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setCartId(cart.getCartId());

        List<CartResponse.CartItemDetail> details = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart.getCartItems()) {
            CartResponse.CartItemDetail detail = new CartResponse.CartItemDetail();
            detail.setCartItemId(item.getCartItemId());
            detail.setProductId(item.getProduct().getProductId());
            detail.setProductName(item.getProduct().getName());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getProduct().getPrice());
            details.add(detail);
            total = total.add(item.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        response.setItems(details);
        response.setTotalAmount(total);
        return response;
    }
}