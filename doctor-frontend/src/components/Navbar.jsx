import { getRole, getName, clearUser } from "../utils/auth";

export default function Navbar({ onNavigate, page }) {
  const role = getRole();
  const name = getName();

  const handleLogout = () => {
    clearUser();
    onNavigate("login");
  };

  const navLinks = {
    PATIENT: [
      { key: "doctors", label: "Find Doctors" },
      { key: "my-appointments", label: "My Appointments" },
    ],
    DOCTOR: [
      { key: "doctor-appointments", label: "My Appointments" },
      { key: "manage-slots", label: "Manage Slots" },
    ],
    ADMIN: [
      { key: "admin", label: "Dashboard" },
      { key: "doctors", label: "Doctors" },
      { key: "admin-appointments", label: "All Appointments" },
    ],
  };

  const links = navLinks[role] || [];

  return (
    <nav style={{
      background: "var(--surface)",
      borderBottom: "1px solid var(--border)",
      padding: "0 24px",
      display: "flex",
      alignItems: "center",
      justifyContent: "space-between",
      height: 60,
      position: "sticky",
      top: 0,
      zIndex: 100,
      boxShadow: "var(--shadow-sm)",
    }}>
      <div style={{ display: "flex", alignItems: "center", gap: 32 }}>
        <button
          onClick={() => onNavigate(role === "ADMIN" ? "admin" : role === "DOCTOR" ? "doctor-appointments" : "doctors")}
          style={{
            background: "none",
            display: "flex",
            alignItems: "center",
            gap: 8,
            fontFamily: "var(--font-display)",
            fontSize: 20,
            color: "var(--accent)",
          }}
        >
          <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <path d="M22 12h-4l-3 9L9 3l-3 9H2"/>
          </svg>
          MediBook
        </button>

        <div style={{ display: "flex", gap: 4 }}>
          {links.map(link => (
            <button
              key={link.key}
              onClick={() => onNavigate(link.key)}
              style={{
                background: page === link.key ? "var(--accent-bg)" : "none",
                color: page === link.key ? "var(--accent)" : "var(--text-soft)",
                padding: "6px 14px",
                borderRadius: "var(--radius-sm)",
                fontSize: 14,
                fontWeight: page === link.key ? 500 : 400,
              }}
            >
              {link.label}
            </button>
          ))}
        </div>
      </div>

      <div style={{ display: "flex", alignItems: "center", gap: 12 }}>
        <div style={{ textAlign: "right" }}>
          <div style={{ fontSize: 14, fontWeight: 500 }}>{name}</div>
          <div style={{ fontSize: 12, color: "var(--text-muted)" }}>{role}</div>
        </div>
        <button className="btn btn-secondary btn-sm" onClick={handleLogout}>
          Logout
        </button>
      </div>
    </nav>
  );
}