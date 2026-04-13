const BASE_URL = "http://localhost:8080";

export const login = async (data) => {
    return fetch(`${BASE_URL}/auth/login`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    }).then(res => res.json());
};