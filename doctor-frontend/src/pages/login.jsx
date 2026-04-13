import { useState } from "react";
import { login } from "../services/api";
import { saveToken } from "../utils/auth";

export default function Login() {

    const [form, setForm] = useState({
        email: "",
        password: ""
    });

    const handleLogin = async () => {
        try {
            const res = await login(form);

            console.log("Response:", res);

            // ⚠️ adjust if backend uses different key
            const token = res.token || res.accessToken;

            if (token) {
                saveToken(token);
                alert("Login Success");
            } else {
                alert("Token not found in response");
            }

        } catch (error) {
            console.error(error);
            alert("Login failed or server error");
        }
    };

    return (
        <div style={{ padding: "20px" }}>
            <h2>Login</h2>

            <input
                type="email"
                placeholder="Email"
                value={form.email}
                onChange={(e) =>
                    setForm({ ...form, email: e.target.value })
                }
            />

            <br /><br />

            <input
                type="password"
                placeholder="Password"
                value={form.password}
                onChange={(e) =>
                    setForm({ ...form, password: e.target.value })
                }
            />

            <br /><br />

            <button onClick={handleLogin}>
                Login
            </button>
        </div>
    );
}