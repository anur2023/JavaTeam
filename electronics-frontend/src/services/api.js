// services/api.js
// Central fetch wrapper. Automatically attaches Authorization header
// when a token exists in localStorage.

import { getToken } from '../utils/auth';

const BASE_URL = 'http://localhost:5000';

async function request(method, path, body = null) {
    const token = getToken();

    const headers = {
        'Content-Type': 'application/json',
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const options = {
        method,
        headers,
    };

    if (body) {
        options.body = JSON.stringify(body);
    }

    const response = await fetch(`${BASE_URL}${path}`, options);

    // Parse JSON response
    const data = await response.json().catch(() => null);

    if (!response.ok) {
        // Throw a readable error
        const message = data?.message || `Error ${response.status}`;
        throw new Error(message);
    }

    return data;
}

// Convenience methods
export const api = {
    get:    (path)        => request('GET',    path),
    post:   (path, body)  => request('POST',   path, body),
    put:    (path, body)  => request('PUT',    path, body),
    delete: (path)        => request('DELETE', path),
};