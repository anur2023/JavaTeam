import { useState } from "react";
import { login } from "../services/api";
import { saveToken } from "../utils/auth";

export default function Login() {
    const [form, setForm] = useState({ email: "", password: "" });

    const handleLogin = async () => {
        const res = await login(form);
        saveToken(res.token);
        alert("Login success");
    };

    return (
        <div>
            <h2>Login</h2>

            <input placeholder="Email"
                   onChange={e => setForm({...form, email: e.target.value})} />

            <input placeholder="Password" type="password"
                   onChange={e => setForm({...form, password: e.target.value})} />

            <button onClick={handleLogin}>Login</button>
        </div>
    );
}