// utils/auth.js
// Simple helpers to store/read/remove the JWT token in localStorage

export function getToken() {
    return localStorage.getItem('token');
}

export function setToken(token) {
    localStorage.setItem('token', token);
}

export function removeToken() {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('role');
}

export function saveUserInfo(token, role, userId) {
    localStorage.setItem('token', token);
    localStorage.setItem('role', role);
    if (userId) localStorage.setItem('userId', String(userId));
}

export function getUserId() {
    return localStorage.getItem('userId');
}

export function getRole() {
    return localStorage.getItem('role');
}

export function isLoggedIn() {
    return !!getToken();
}