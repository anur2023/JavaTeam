import { useState, useEffect } from "react";
import { getDoctorAppointments, updateAppointmentStatus } from "../services/api";

const STATUSES = ["PENDING", "CONFIRMED", "CANCELLED", "COMPLETED", "NO_SHOW"];

const statusBadge = (status) => {
  const map = { PENDING: "amber", CONFIRMED: "green", CANCELLED: "red", COMPLETED: "blue", NO_SHOW: "gray" };
  return <span className={`badge badge-${map[status] || "gray"}`}>{status}</span>;
};

export default function DoctorAppointments() {
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [updating, setUpdating] = useState(null);

  useEffect(() => {
    load();
  }, []);

  const load = async () => {
    try {
      const data = await getDoctorAppointments();
      setAppointments(data);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  const handleStatusChange = async (id, status) => {
    setUpdating(id);
    try {
      const updated = await updateAppointmentStatus(id, status);
      setAppointments(prev => prev.map(a => a.id === id ? updated : a));
    } catch (e) {
      setError(e.message);
    } finally {
      setUpdating(null);
    }
  };

  const stats = {
    total: appointments.length,
    pending: appointments.filter(a => a.status === "PENDING").length,
    confirmed: appointments.filter(a => a.status === "CONFIRMED").length,
    completed: appointments.filter(a => a.status === "COMPLETED").length,
  };

  return (
    <div className="page-wrapper">
      <h1 className="page-title">My Appointments</h1>
      <p className="page-subtitle">Manage patient appointments</p>

      <div className="grid-4" style={{ marginBottom: 28 }}>
        {[
          { label: "Total", value: stats.total, color: "var(--text)" },
          { label: "Pending", value: stats.pending, color: "var(--amber)" },
          { label: "Confirmed", value: stats.confirmed, color: "var(--accent)" },
          { label: "Completed", value: stats.completed, color: "var(--blue)" },
        ].map(s => (
          <div key={s.label} className="card" style={{ padding: "20px 24px" }}>
            <div style={{ fontSize: 28, fontFamily: "var(--font-display)", color: s.color }}>{s.value}</div>
            <div style={{ fontSize: 13, color: "var(--text-soft)", marginTop: 4 }}>{s.label}</div>
          </div>
        ))}
      </div>

      {error && <div className="error-msg">{error}</div>}

      {loading ? (
        <div className="loading-screen"><span className="spinner" /> Loading appointments...</div>
      ) : appointments.length === 0 ? (
        <div className="empty-state">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5"><rect x="3" y="4" width="18" height="18" rx="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/></svg>
          <span>No appointments yet</span>
        </div>
      ) : (
        <div className="table-wrapper">
          <table>
            <thead>
              <tr>
                <th>Patient ID</th>
                <th>Date</th>
                <th>Time</th>
                <th>Mode</th>
                <th>Notes</th>
                <th>Status</th>
                <th>Update</th>
              </tr>
            </thead>
            <tbody>
              {appointments.map(apt => (
                <tr key={apt.id}>
                  <td>#{apt.patientId}</td>
                  <td>{apt.slotDate}</td>
                  <td style={{ fontSize: 13 }}>{apt.startTime} – {apt.endTime}</td>
                  <td>
                    <span className={`badge badge-${apt.mode === "ONLINE" ? "blue" : "amber"}`}>{apt.mode}</span>
                  </td>
                  <td style={{ maxWidth: 160, fontSize: 13, color: "var(--text-soft)" }}>
                    {apt.notes || <span style={{ color: "var(--text-muted)" }}>—</span>}
                  </td>
                  <td>{statusBadge(apt.status)}</td>
                  <td>
                    <select
                      className="input"
                      style={{ width: 140, padding: "5px 10px", fontSize: 13 }}
                      value={apt.status}
                      onChange={e => handleStatusChange(apt.id, e.target.value)}
                      disabled={updating === apt.id}
                    >
                      {STATUSES.map(s => <option key={s} value={s}>{s}</option>)}
                    </select>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}