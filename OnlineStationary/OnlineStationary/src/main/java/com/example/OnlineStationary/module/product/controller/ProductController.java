package com.example.OnlineStationary.module.product.controller;

import com.example.OnlineStationary.module.product.dto.ProductDTO;
import com.example.OnlineStationary.module.product.entity.BulkPricing;
import com.example.OnlineStationary.module.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO dto) {
        return productService.createProduct(dto);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/inventory/{productId}")
    public String updateStock(@PathVariable Long productId,
                              @RequestParam Integer quantity) {
        productService.updateStock(productId, quantity);
        return "Stock updated successfully";
    }


    @PostMapping("/bulk-pricing/{productId}")
    public BulkPricing addBulkPricing(@PathVariable Long productId,
                                      @RequestParam Integer minQty,
                                      @RequestParam Double price) {
        return productService.addBulkPricing(productId, minQty, price);
    }

    @GetMapping("/bulk-pricing/{productId}")
    public List<BulkPricing> getBulkPricing(@PathVariable Long productId) {
        return productService.getBulkPricing(productId);
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }

    @GetMapping("/search")
    public List<ProductDTO> searchProducts(@RequestParam String keyword) {
        return productService.searchProducts(keyword);
    }

    @GetMapping("/filter/category/{categoryId}")
    public List<ProductDTO> filterByCategory(@PathVariable Long categoryId) {
        return productService.filterByCategory(categoryId);
    }

    @GetMapping("/filter/brand/{brandId}")
    public List<ProductDTO> filterByBrand(@PathVariable Long brandId) {
        return productService.filterByBrand(brandId);
    }

    @GetMapping("/filter/price")
    public List<ProductDTO> filterByPrice(@RequestParam Double min,
                                          @RequestParam Double max) {
        return productService.filterByPrice(min, max);
    }
}