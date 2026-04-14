package com.ecommerce.electronic.module.product.service;

import com.ecommerce.electronic.module.product.dto.CartItemRequest;
import com.ecommerce.electronic.module.product.dto.CartItemResponse;
import com.ecommerce.electronic.module.product.dto.CartResponse;
import com.ecommerce.electronic.module.product.entity.Cart;
import com.ecommerce.electronic.module.product.entity.CartItem;
import com.ecommerce.electronic.module.product.entity.Product;
import com.ecommerce.electronic.module.product.repository.CartItemRepository;
import com.ecommerce.electronic.module.product.repository.CartRepository;
import com.ecommerce.electronic.module.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    // ── ADD ITEM TO CART ─────────────────────────────────────────────────────
    @Transactional
    public CartResponse addItemToCart(CartItemRequest request) {

        // Validate quantity
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be at least 1.");
        }

        // Validate product and stock
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + request.getProductId()));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock. Available: " + product.getStock());
        }

        // Get or create cart for user
        Cart cart = cartRepository.findByUserId(request.getUserId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(request.getUserId());
                    return cartRepository.save(newCart);
                });

        // Check if product already in cart → update quantity
        Optional<CartItem> existingItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQty = item.getQuantity() + request.getQuantity();

            if (product.getStock() < newQty) {
                throw new RuntimeException("Insufficient stock. Available: " + product.getStock());
            }

            item.setQuantity(newQty);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity());
            newItem.setPriceAtAdded(product.getPrice());
            cartItemRepository.save(newItem);
        }

        return buildCartResponse(cart.getId());
    }

    // ── GET CART ─────────────────────────────────────────────────────────────
    public CartResponse getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for userId: " + userId));

        return buildCartResponse(cart.getId());
    }

    // ── UPDATE ITEM QUANTITY ─────────────────────────────────────────────────
    @Transactional
    public CartResponse updateItemQuantity(CartItemRequest request) {
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be at least 1.");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + request.getProductId()));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock. Available: " + product.getStock());
        }

        Cart cart = cartRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Cart not found for userId: " + request.getUserId()));

        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), request.getProductId())
                .orElseThrow(() -> new RuntimeException("Item not found in cart."));

        item.setQuantity(request.getQuantity());
        cartItemRepository.save(item);

        return buildCartResponse(cart.getId());
    }

    // ── REMOVE ITEM FROM CART ────────────────────────────────────────────────
    @Transactional
    public CartResponse removeItemFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for userId: " + userId));

        cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Item not found in cart."));

        cartItemRepository.deleteByCartIdAndProductId(cart.getId(), productId);

        return buildCartResponse(cart.getId());
    }

    // ── CLEAR ENTIRE CART ────────────────────────────────────────────────────
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for userId: " + userId));

        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    // ── HELPER: Build CartResponse ───────────────────────────────────────────
    private CartResponse buildCartResponse(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found."));

        List<CartItem> items = cartItemRepository.findByCartId(cartId);

        List<CartItemResponse> itemResponses = items.stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList());

        BigDecimal grandTotal = itemResponses.stream()
                .map(CartItemResponse::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartResponse response = new CartResponse();
        response.setCartId(cart.getId());
        response.setUserId(cart.getUserId());
        response.setItems(itemResponses);
        response.setGrandTotal(grandTotal);
        response.setCreatedAt(cart.getCreatedAt());
        response.setUpdatedAt(cart.getUpdatedAt());

        return response;
    }

    // ── MAPPER ───────────────────────────────────────────────────────────────
    private CartItemResponse toItemResponse(CartItem item) {
        CartItemResponse response = new CartItemResponse();
        response.setCartItemId(item.getId());
        response.setProductId(item.getProduct().getId());
        response.setProductName(item.getProduct().getName());
        response.setProductImageUrl(item.getProduct().getImageUrl());
        response.setQuantity(item.getQuantity());
        response.setPriceAtAdded(item.getPriceAtAdded());
        response.setSubTotal(item.getPriceAtAdded()
                .multiply(BigDecimal.valueOf(item.getQuantity())));
        return response;
    }
}