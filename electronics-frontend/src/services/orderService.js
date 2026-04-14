// services/orderService.js
// All order-related API calls.

import { api } from './api';

export const orderService = {
    // POST /orders  { userId, items: [{ productId, quantity, price }] }
    placeOrder(userId, items) {
        return api.post('/orders', { userId, items });
    },

    // GET /orders/user/:userId
    getOrdersByUser(userId) {
        return api.get(`/orders/user/${userId}`);
    },

    // GET /orders/:id
    getOrderById(id) {
        return api.get(`/orders/${id}`);
    },

    // PUT /orders/cancel/:orderId
    cancelOrder(orderId) {
        return api.put(`/orders/cancel/${orderId}`);
    },
};