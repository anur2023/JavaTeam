// services/authService.js
// Handles register and login API calls.

import { api } from './api';

export const authService = {
    // POST /auth/register
    register(name, email, password, role = 'CUSTOMER', phone = '') {
        return api.post('/auth/register', { name, email, password, role, phone });
    },

    // POST /auth/login
    login(email, password) {
        return api.post('/auth/login', { email, password });
    },
};