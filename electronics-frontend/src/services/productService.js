// services/productService.js
// All product-related API calls.

import { api } from './api';

export const productService = {
    // GET /products
    getAllProducts() {
        return api.get('/products');
    },

    // GET /products?categoryId=X
    getByCategory(categoryId) {
        return api.get(`/products?categoryId=${categoryId}`);
    },

    // GET /products?search=X
    searchProducts(query) {
        return api.get(`/products?search=${encodeURIComponent(query)}`);
    },

    // GET /products/:id
    getProductById(id) {
        return api.get(`/products/${id}`);
    },

    // GET /categories
    getAllCategories() {
        return api.get('/categories');
    },
};