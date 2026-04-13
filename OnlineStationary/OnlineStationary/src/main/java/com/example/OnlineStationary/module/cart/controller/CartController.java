package com.example.OnlineStationary.module.cart.controller;

import com.example.OnlineStationary.module.cart.dto.CartItemRequest;
import com.example.OnlineStationary.module.cart.dto.CartResponse;
import com.example.OnlineStationary.module.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // GET /api/cart?userId=1
    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestParam Long userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    // POST /api/cart/add?userId=1
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@RequestParam Long userId,
                                                  @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addToCart(userId, request));
    }

    // PUT /api/cart/update/{cartItemId}?quantity=5
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartResponse> updateCartItem(@PathVariable Long cartItemId,
                                                       @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(cartItemId, quantity));
    }

    // DELETE /api/cart/remove/{cartItemId}
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok("Item removed from cart");
    }
}