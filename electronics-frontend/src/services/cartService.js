// services/cartService.js
// All cart-related API calls.

import { api } from './api';

export const cartService = {
    // GET /cart?userId=X
    getCart(userId) {
        return api.get(`/cart?userId=${userId}`);
    },

    // POST /cart/add  { userId, productId, quantity }
    addItem(userId, productId, quantity = 1) {
        return api.post('/cart/add', { userId, productId, quantity });
    },

    // PUT /cart/update  { userId, productId, quantity }
    updateItem(userId, productId, quantity) {
        return api.put('/cart/update', { userId, productId, quantity });
    },

    // DELETE /cart/remove?userId=X&productId=Y
    removeItem(userId, productId) {
        return api.delete(`/cart/remove?userId=${userId}&productId=${productId}`);
    },

    // DELETE /cart/clear?userId=X
    clearCart(userId) {
        return api.delete(`/cart/clear?userId=${userId}`);
    },
};