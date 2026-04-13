package com.example.OnlineStationary.module.product.service;

import com.example.OnlineStationary.module.product.dto.ProductDTO;
import com.example.OnlineStationary.module.product.entity.*;
import com.example.OnlineStationary.module.product.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private BrandRepository brandRepository;
    @Autowired private InventoryRepository inventoryRepository;
    @Autowired private BulkPricingRepository bulkPricingRepository;

    public ProductDTO createProduct(ProductDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(category);
        product.setBrand(brand);

        Product savedProduct = productRepository.save(product);

        Inventory inventory = new Inventory();
        inventory.setProduct(savedProduct);
        inventory.setStockQuantity(dto.getStockQuantity() != null ? dto.getStockQuantity() : 0);
        inventoryRepository.save(inventory);

        return mapToDTO(savedProduct, inventory);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(p -> {
            Inventory inventory = inventoryRepository.findByProduct(p).orElse(null);
            return mapToDTO(p, inventory);
        }).collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Inventory inventory = inventoryRepository.findByProduct(product).orElse(null);
        return mapToDTO(product, inventory);
    }

    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(category);
        product.setBrand(brand);

        Product updatedProduct = productRepository.save(product);

        Inventory inventory = inventoryRepository.findByProduct(updatedProduct)
                .orElse(new Inventory());
        inventory.setProduct(updatedProduct);
        inventory.setStockQuantity(dto.getStockQuantity() != null ? dto.getStockQuantity() : 0);
        inventoryRepository.save(inventory);

        return mapToDTO(updatedProduct, inventory);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDTO> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword).stream().map(p -> {
            Inventory inventory = inventoryRepository.findByProduct(p).orElse(null);
            return mapToDTO(p, inventory);
        }).collect(Collectors.toList());
    }

    public List<ProductDTO> filterByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return productRepository.findByCategory(category).stream().map(p -> {
            Inventory inventory = inventoryRepository.findByProduct(p).orElse(null);
            return mapToDTO(p, inventory);
        }).collect(Collectors.toList());
    }

    public List<ProductDTO> filterByBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        return productRepository.findByBrand(brand).stream().map(p -> {
            Inventory inventory = inventoryRepository.findByProduct(p).orElse(null);
            return mapToDTO(p, inventory);
        }).collect(Collectors.toList());
    }

    public List<ProductDTO> filterByPrice(Double min, Double max) {
        return productRepository.findByPriceBetween(
                BigDecimal.valueOf(min), BigDecimal.valueOf(max)).stream().map(p -> {
            Inventory inventory = inventoryRepository.findByProduct(p).orElse(null);
            return mapToDTO(p, inventory);
        }).collect(Collectors.toList());
    }

    public void updateStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Inventory inventory = inventoryRepository.findByProduct(product)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inventory.setStockQuantity(quantity);
        inventoryRepository.save(inventory);
    }

    public BulkPricing addBulkPricing(Long productId, Integer minQty, Double price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        BulkPricing bulk = new BulkPricing();
        bulk.setProduct(product);
        bulk.setMinQuantity(minQty);
        bulk.setDiscountedPrice(price);
        return bulkPricingRepository.save(bulk);
    }

    public List<BulkPricing> getBulkPricing(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return bulkPricingRepository.findByProductOrderByMinQuantityAsc(product);
    }

    private ProductDTO mapToDTO(Product product, Inventory inventory) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategoryId(product.getCategory().getCategoryId());
        dto.setCategoryName(product.getCategory().getName());
        dto.setBrandId(product.getBrand().getBrandId());
        dto.setBrandName(product.getBrand().getName());
        dto.setStockQuantity(inventory != null ? inventory.getStockQuantity() : 0);
        return dto;
    }
}