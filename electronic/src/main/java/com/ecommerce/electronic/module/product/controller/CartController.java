package com.ecommerce.electronic.module.product.controller;

import com.ecommerce.electronic.module.product.dto.CartItemRequest;
import com.ecommerce.electronic.module.product.dto.CartResponse;
import com.ecommerce.electronic.module.product.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add item to cart
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addItem(@RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addItemToCart(request));
    }

    // Get cart by userId
    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestParam Long userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    // Update item quantity
    @PutMapping("/update")
    public ResponseEntity<CartResponse> updateItem(@RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.updateItemQuantity(request));
    }

    // Remove specific item from cart
    @DeleteMapping("/remove")
    public ResponseEntity<CartResponse> removeItem(
            @RequestParam Long userId,
            @RequestParam Long productId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(userId, productId));
    }

    // Clear entire cart
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@RequestParam Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}