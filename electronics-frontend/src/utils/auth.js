export function getToken() {
    return localStorage.getItem('token');
}

export function removeToken() {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('role');
    localStorage.removeItem('name');
}

export function saveUserInfo(token, role, userId, name) {
    localStorage.setItem('token', token);
    localStorage.setItem('role', role);
    if (userId) localStorage.setItem('userId', String(userId));
    if (name)   localStorage.setItem('name', name);
}

export function getUserId() {
    return localStorage.getItem('userId');
}

export function getRole() {
    return localStorage.getItem('role');
}

export function getUserName() {
    return localStorage.getItem('name');
}

export function isLoggedIn() {
    return !!getToken();
}

export function isAdmin() {
    return getRole() === 'ADMIN';
}

export function isVendor() {
    return getRole() === 'VENDOR';
}

export function isCustomer() {
    return getRole() === 'CUSTOMER';
}