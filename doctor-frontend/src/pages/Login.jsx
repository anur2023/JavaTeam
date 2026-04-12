import { useState } from "react";
import { login } from "../services/api";
import { saveUser } from "../utils/auth";

export default function Login({ onNavigate }) {
  const [form, setForm] = useState({ email: "", password: "" });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);
    try {
      const res = await login(form);
      saveUser({ token: res.token, role: res.role, name: res.name });
      const dest = res.role === "ADMIN" ? "admin" : res.role === "DOCTOR" ? "doctor-appointments" : "doctors";
      onNavigate(dest);
    } catch (err) {
      setError(err.message || "Invalid credentials");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{
      minHeight: "100vh",
      display: "flex",
      background: "var(--bg)",
    }}>
      {/* Left panel */}
      <div style={{
        flex: 1,
        background: "var(--accent)",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        padding: "60px",
        color: "#fff",
      }}>
        <div style={{ maxWidth: 400 }}>
          <div style={{ display: "flex", alignItems: "center", gap: 10, marginBottom: 48 }}>
            <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <path d="M22 12h-4l-3 9L9 3l-3 9H2"/>
            </svg>
            <span style={{ fontFamily: "var(--font-display)", fontSize: 26 }}>MediBook</span>
          </div>
          <h1 style={{ fontFamily: "var(--font-display)", fontSize: 42, lineHeight: 1.2, marginBottom: 20, fontWeight: 400 }}>
            Your health,<br /><em>simplified.</em>
          </h1>
          <p style={{ fontSize: 16, opacity: 0.8, lineHeight: 1.7 }}>
            Book appointments with top specialists, manage your health records, and connect with doctors — all in one place.
          </p>

          <div style={{ marginTop: 48, display: "flex", flexDirection: "column", gap: 20 }}>
            {["Find & book appointments instantly", "Real-time slot availability", "Online & offline consultations"].map(t => (
              <div key={t} style={{ display: "flex", alignItems: "center", gap: 12, fontSize: 14, opacity: 0.9 }}>
                <div style={{ width: 6, height: 6, borderRadius: "50%", background: "rgba(255,255,255,0.6)", flexShrink: 0 }} />
                {t}
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Right panel */}
      <div style={{
        width: 440,
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        padding: "60px 48px",
        background: "var(--surface)",
      }}>
        <h2 style={{ fontFamily: "var(--font-display)", fontSize: 30, marginBottom: 6, fontWeight: 400 }}>Welcome back</h2>
        <p style={{ fontSize: 14, color: "var(--text-soft)", marginBottom: 32 }}>Sign in to your account</p>

        {error && <div className="error-msg">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="label">Email address</label>
            <input
              className="input"
              type="email"
              placeholder="doctor@example.com"
              value={form.email}
              onChange={e => setForm({ ...form, email: e.target.value })}
              required
            />
          </div>
          <div className="form-group">
            <label className="label">Password</label>
            <input
              className="input"
              type="password"
              placeholder="••••••••"
              value={form.password}
              onChange={e => setForm({ ...form, password: e.target.value })}
              required
            />
          </div>

          <button
            type="submit"
            className="btn btn-primary btn-lg"
            style={{ width: "100%", marginTop: 8 }}
            disabled={loading}
          >
            {loading ? <span className="spinner" /> : "Sign In"}
          </button>
        </form>

        <div style={{ textAlign: "center", marginTop: 24, fontSize: 14, color: "var(--text-soft)" }}>
          Don't have an account?{" "}
          <button
            onClick={() => onNavigate("register")}
            style={{ background: "none", color: "var(--accent)", fontWeight: 500, fontSize: 14 }}
          >
            Register
          </button>
        </div>
      </div>
    </div>
  );
}