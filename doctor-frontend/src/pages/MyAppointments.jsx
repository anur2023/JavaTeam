import { useState, useEffect } from "react";
import { getUserAppointments } from "../services/api";

const statusBadge = (status) => {
  const map = { PENDING: "amber", CONFIRMED: "green", CANCELLED: "red", COMPLETED: "blue", NO_SHOW: "gray" };
  return <span className={`badge badge-${map[status] || "gray"}`}>{status}</span>;
};

export default function MyAppointments() {
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    (async () => {
      try {
        const data = await getUserAppointments();
        setAppointments(data);
      } catch (e) {
        setError(e.message);
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  return (
    <div className="page-wrapper">
      <h1 className="page-title">My Appointments</h1>
      <p className="page-subtitle">Track your upcoming and past consultations</p>

      {error && <div className="error-msg">{error}</div>}

      {loading ? (
        <div className="loading-screen"><span className="spinner" /> Loading...</div>
      ) : appointments.length === 0 ? (
        <div className="empty-state">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/></svg>
          <span>No appointments yet</span>
        </div>
      ) : (
        <div style={{ display: "flex", flexDirection: "column", gap: 12 }}>
          {appointments.map(apt => (
            <div key={apt.id} className="card" style={{ padding: 20 }}>
              <div className="flex-between">
                <div style={{ display: "flex", gap: 20, alignItems: "flex-start" }}>
                  <div style={{
                    width: 44, height: 44, borderRadius: "var(--radius-sm)",
                    background: "var(--accent-bg)", color: "var(--accent)",
                    display: "flex", alignItems: "center", justifyContent: "center",
                    flexShrink: 0,
                  }}>
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                  </div>
                  <div>
                    <div style={{ fontWeight: 500, marginBottom: 4 }}>Doctor #{apt.doctorId}</div>
                    <div style={{ fontSize: 13, color: "var(--text-soft)", display: "flex", gap: 16 }}>
                      <span>📅 {apt.slotDate}</span>
                      <span>🕐 {apt.startTime} – {apt.endTime}</span>
                      <span className={`badge badge-${apt.mode === "ONLINE" ? "blue" : "amber"}`}>{apt.mode}</span>
                    </div>
                    {apt.notes && (
                      <div style={{ fontSize: 13, color: "var(--text-muted)", marginTop: 6, fontStyle: "italic" }}>"{apt.notes}"</div>
                    )}
                  </div>
                </div>
                <div style={{ display: "flex", flexDirection: "column", alignItems: "flex-end", gap: 6 }}>
                  {statusBadge(apt.status)}
                  <span style={{ fontSize: 12, color: "var(--text-muted)" }}>
                    Booked {new Date(apt.createdAt).toLocaleDateString()}
                  </span>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}