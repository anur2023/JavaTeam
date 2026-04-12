import { useState, useEffect } from "react";
import { getDailyReport, getRevenueReport, getSpecialtyStats, getAdminAppointments, createDoctor, createSpecialty, addDoctorSpecialty, getSpecialties } from "../services/api";

export default function AdminDashboard() {
  const [daily, setDaily] = useState(null);
  const [revenue, setRevenue] = useState(null);
  const [stats, setStats] = useState([]);
  const [loading, setLoading] = useState(true);
  const [tab, setTab] = useState("overview");

  // Admin actions state
  const [specialties, setSpecialties] = useState([]);
  const [docForm, setDocForm] = useState({ userId: "", experienceYears: "", consultationFeeOnline: "", consultationFeeOffline: "" });
  const [specForm, setSpecForm] = useState({ name: "", description: "" });
  const [dsForm, setDsForm] = useState({ doctorId: "", specialtyId: "" });
  const [actionMsg, setActionMsg] = useState({ type: "", text: "" });
  const [actionLoading, setActionLoading] = useState(false);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const [d, r, s, sp] = await Promise.all([
        getDailyReport(), getRevenueReport(), getSpecialtyStats(), getSpecialties()
      ]);
      setDaily(d);
      setRevenue(r);
      setStats(s);
      setSpecialties(sp);
    } catch (e) {
      console.error(e);
    } finally {
      setLoading(false);
    }
  };

  const msg = (type, text) => {
    setActionMsg({ type, text });
    setTimeout(() => setActionMsg({ type: "", text: "" }), 3000);
  };

  const handleCreateDoctor = async (e) => {
    e.preventDefault();
    setActionLoading(true);
    try {
      await createDoctor({
        userId: Number(docForm.userId),
        experienceYears: Number(docForm.experienceYears),
        consultationFeeOnline: Number(docForm.consultationFeeOnline),
        consultationFeeOffline: Number(docForm.consultationFeeOffline),
      });
      msg("success", "Doctor profile created!");
      setDocForm({ userId: "", experienceYears: "", consultationFeeOnline: "", consultationFeeOffline: "" });
    } catch (e) {
      msg("error", e.message);
    } finally {
      setActionLoading(false);
    }
  };

  const handleCreateSpecialty = async (e) => {
    e.preventDefault();
    setActionLoading(true);
    try {
      await createSpecialty(specForm);
      msg("success", "Specialty created!");
      setSpecForm({ name: "", description: "" });
      const sp = await getSpecialties();
      setSpecialties(sp);
    } catch (e) {
      msg("error", e.message);
    } finally {
      setActionLoading(false);
    }
  };

  const handleAddDoctorSpecialty = async (e) => {
    e.preventDefault();
    setActionLoading(true);
    try {
      await addDoctorSpecialty(dsForm.doctorId, dsForm.specialtyId);
      msg("success", "Specialty assigned to doctor!");
      setDsForm({ doctorId: "", specialtyId: "" });
    } catch (e) {
      msg("error", e.message);
    } finally {
      setActionLoading(false);
    }
  };

  const StatCard = ({ label, value, sub, color = "var(--text)" }) => (
    <div className="card" style={{ padding: 24 }}>
      <div style={{ fontSize: 32, fontFamily: "var(--font-display)", color }}>{value ?? "—"}</div>
      <div style={{ fontSize: 14, fontWeight: 500, marginTop: 4 }}>{label}</div>
      {sub && <div style={{ fontSize: 12, color: "var(--text-muted)", marginTop: 4 }}>{sub}</div>}
    </div>
  );

  const tabs = [
    { key: "overview", label: "Overview" },
    { key: "doctors", label: "Add Doctor" },
    { key: "specialties", label: "Specialties" },
  ];

  return (
    <div className="page-wrapper">
      <h1 className="page-title">Admin Dashboard</h1>
      <p className="page-subtitle">System overview and management</p>

      {/* Tabs */}
      <div style={{ display: "flex", gap: 4, marginBottom: 28, background: "var(--surface2)", padding: 4, borderRadius: "var(--radius-sm)", width: "fit-content" }}>
        {tabs.map(t => (
          <button
            key={t.key}
            onClick={() => setTab(t.key)}
            style={{
              padding: "7px 18px",
              borderRadius: "var(--radius-sm)",
              fontSize: 14,
              fontWeight: tab === t.key ? 500 : 400,
              background: tab === t.key ? "var(--surface)" : "none",
              color: tab === t.key ? "var(--text)" : "var(--text-soft)",
              boxShadow: tab === t.key ? "var(--shadow-sm)" : "none",
            }}
          >
            {t.label}
          </button>
        ))}
      </div>

      {loading ? (
        <div className="loading-screen"><span className="spinner" /> Loading...</div>
      ) : tab === "overview" ? (
        <>
          <div style={{ marginBottom: 28 }}>
            <div style={{ fontSize: 12, fontWeight: 600, color: "var(--text-soft)", textTransform: "uppercase", letterSpacing: "0.06em", marginBottom: 14 }}>Today's Activity</div>
            <div className="grid-3">
              <StatCard label="Total Appointments" value={daily?.totalAppointments} />
              <StatCard label="Online" value={daily?.onlineAppointments} color="var(--blue)" />
              <StatCard label="Offline" value={daily?.offlineAppointments} color="var(--amber)" />
            </div>
          </div>
          <div style={{ marginBottom: 28 }}>
            <div style={{ fontSize: 12, fontWeight: 600, color: "var(--text-soft)", textTransform: "uppercase", letterSpacing: "0.06em", marginBottom: 14 }}>Revenue</div>
            <div className="grid-3">
              <StatCard label="Total Revenue" value={revenue ? `₹${revenue.totalRevenue?.toFixed(0)}` : "—"} color="var(--accent)" />
              <StatCard label="Online Revenue" value={revenue ? `₹${revenue.onlineRevenue?.toFixed(0)}` : "—"} color="var(--blue)" />
              <StatCard label="Offline Revenue" value={revenue ? `₹${revenue.offlineRevenue?.toFixed(0)}` : "—"} color="var(--amber)" />
            </div>
          </div>
          {stats.length > 0 && (
            <div>
              <div style={{ fontSize: 12, fontWeight: 600, color: "var(--text-soft)", textTransform: "uppercase", letterSpacing: "0.06em", marginBottom: 14 }}>Appointments by Specialty</div>
              <div className="table-wrapper">
                <table>
                  <thead><tr><th>Specialty</th><th>Appointments</th></tr></thead>
                  <tbody>
                    {stats.map(s => (
                      <tr key={s.specialtyName}>
                        <td>{s.specialtyName}</td>
                        <td><span className="badge badge-green">{s.appointmentCount}</span></td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}
        </>
      ) : tab === "doctors" ? (
        <div style={{ maxWidth: 480 }}>
          <div className="card" style={{ padding: 28 }}>
            <h3 style={{ fontFamily: "var(--font-display)", fontSize: 20, fontWeight: 400, marginBottom: 20 }}>Create Doctor Profile</h3>
            {actionMsg.text && <div className={actionMsg.type === "error" ? "error-msg" : "success-msg"}>{actionMsg.text}</div>}
            <form onSubmit={handleCreateDoctor}>
              <div className="form-group">
                <label className="label">User ID</label>
                <input className="input" type="number" placeholder="User ID from registration" value={docForm.userId} onChange={e => setDocForm({ ...docForm, userId: e.target.value })} required />
              </div>
              <div className="form-group">
                <label className="label">Years of Experience</label>
                <input className="input" type="number" placeholder="5" value={docForm.experienceYears} onChange={e => setDocForm({ ...docForm, experienceYears: e.target.value })} required />
              </div>
              <div className="grid-2">
                <div className="form-group">
                  <label className="label">Online Fee (₹)</label>
                  <input className="input" type="number" placeholder="500" value={docForm.consultationFeeOnline} onChange={e => setDocForm({ ...docForm, consultationFeeOnline: e.target.value })} required />
                </div>
                <div className="form-group">
                  <label className="label">Offline Fee (₹)</label>
                  <input className="input" type="number" placeholder="800" value={docForm.consultationFeeOffline} onChange={e => setDocForm({ ...docForm, consultationFeeOffline: e.target.value })} required />
                </div>
              </div>
              <button type="submit" className="btn btn-primary" style={{ width: "100%" }} disabled={actionLoading}>
                {actionLoading ? <span className="spinner" /> : "Create Doctor"}
              </button>
            </form>

            <div style={{ borderTop: "1px solid var(--border)", margin: "28px 0" }} />

            <h3 style={{ fontFamily: "var(--font-display)", fontSize: 20, fontWeight: 400, marginBottom: 20 }}>Assign Specialty to Doctor</h3>
            <form onSubmit={handleAddDoctorSpecialty}>
              <div className="form-group">
                <label className="label">Doctor ID</label>
                <input className="input" type="number" placeholder="Doctor ID" value={dsForm.doctorId} onChange={e => setDsForm({ ...dsForm, doctorId: e.target.value })} required />
              </div>
              <div className="form-group">
                <label className="label">Specialty</label>
                <select className="input" value={dsForm.specialtyId} onChange={e => setDsForm({ ...dsForm, specialtyId: e.target.value })} required>
                  <option value="">Select Specialty</option>
                  {specialties.map(s => <option key={s.specialtyId} value={s.specialtyId}>{s.name}</option>)}
                </select>
              </div>
              <button type="submit" className="btn btn-secondary" style={{ width: "100%" }} disabled={actionLoading}>
                Assign Specialty
              </button>
            </form>
          </div>
        </div>
      ) : (
        <div style={{ maxWidth: 480 }}>
          <div className="card" style={{ padding: 28 }}>
            <h3 style={{ fontFamily: "var(--font-display)", fontSize: 20, fontWeight: 400, marginBottom: 20 }}>Create Specialty</h3>
            {actionMsg.text && <div className={actionMsg.type === "error" ? "error-msg" : "success-msg"}>{actionMsg.text}</div>}
            <form onSubmit={handleCreateSpecialty}>
              <div className="form-group">
                <label className="label">Name</label>
                <input className="input" placeholder="e.g. Cardiology" value={specForm.name} onChange={e => setSpecForm({ ...specForm, name: e.target.value })} required />
              </div>
              <div className="form-group">
                <label className="label">Description</label>
                <input className="input" placeholder="Brief description" value={specForm.description} onChange={e => setSpecForm({ ...specForm, description: e.target.value })} />
              </div>
              <button type="submit" className="btn btn-primary" style={{ width: "100%" }} disabled={actionLoading}>
                {actionLoading ? <span className="spinner" /> : "Create Specialty"}
              </button>
            </form>

            {specialties.length > 0 && (
              <>
                <div style={{ borderTop: "1px solid var(--border)", margin: "24px 0" }} />
                <div style={{ fontSize: 12, fontWeight: 600, color: "var(--text-soft)", textTransform: "uppercase", letterSpacing: "0.06em", marginBottom: 12 }}>Existing Specialties</div>
                <div style={{ display: "flex", flexWrap: "wrap", gap: 8 }}>
                  {specialties.map(s => (
                    <span key={s.specialtyId} className="badge badge-green">
                      #{s.specialtyId} {s.name}
                    </span>
                  ))}
                </div>
              </>
            )}
          </div>
        </div>
      )}
    </div>
  );
}