// services/paymentService.js
// Handles payment API calls.

import { api } from './api';

export const paymentService = {
    // POST /payments  { orderId, amount, status, transactionId }
    processPayment(orderId, amount) {
        return api.post('/payments', {
            orderId,
            amount,
            status: 'SUCCESS',      // simulated: always success
            transactionId: '',       // backend generates UUID if empty
        });
    },

    // GET /payments/order/:orderId
    getPaymentByOrder(orderId) {
        return api.get(`/payments/order/${orderId}`);
    },
};