import { useState, useEffect } from "react";
import { getAllSlots, createSlot, getProfile, getDoctorByUserId } from "../services/api";

export default function ManageSlots() {
  const [slots, setSlots] = useState([]);
  const [loading, setLoading] = useState(true);
  const [creating, setCreating] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [doctorId, setDoctorId] = useState(null);
  const [form, setForm] = useState({
    slotDate: "",
    startTime: "",
    endTime: "",
    mode: "ONLINE",
  });

  useEffect(() => {
    loadDoctorAndSlots();
  }, []);

  const loadDoctorAndSlots = async () => {
    try {
      const profile = await getProfile();
      const docRes = await getDoctorByUserId(profile.id);
      setDoctorId(docRes.doctorId);
      const slotsData = await getAllSlots(docRes.doctorId);
      setSlots(slotsData);
    } catch (e) {
      setError("Could not load slots. Make sure a doctor profile exists for your account.");
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    if (!doctorId) { setError("Doctor profile not found."); return; }
    setCreating(true);
    setError("");
    setSuccess("");
    try {
      const newSlot = await createSlot({ ...form, doctorId });
      setSlots(prev => [...prev, newSlot]);
      setSuccess("Slot created successfully!");
      setForm({ slotDate: "", startTime: "", endTime: "", mode: "ONLINE" });
    } catch (e) {
      setError(e.message);
    } finally {
      setCreating(false);
    }
  };

  return (
    <div className="page-wrapper">
      <h1 className="page-title">Manage Slots</h1>
      <p className="page-subtitle">Create and view your availability slots</p>

      <div style={{ display: "grid", gridTemplateColumns: "1fr 340px", gap: 24, alignItems: "start" }}>
        {/* Slots list */}
        <div>
          {error && <div className="error-msg">{error}</div>}
          {loading ? (
            <div className="loading-screen"><span className="spinner" /></div>
          ) : slots.length === 0 ? (
            <div className="empty-state card" style={{ padding: "60px 20px" }}>
              <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
              <span>No slots created yet</span>
            </div>
          ) : (
            <div className="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Start</th>
                    <th>End</th>
                    <th>Mode</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {slots.map(slot => (
                    <tr key={slot.id}>
                      <td>{slot.slotDate}</td>
                      <td>{slot.startTime}</td>
                      <td>{slot.endTime}</td>
                      <td>
                        <span className={`badge badge-${slot.mode === "ONLINE" ? "blue" : "amber"}`}>{slot.mode}</span>
                      </td>
                      <td>
                        <span className={`badge badge-${slot.booked ? "red" : "green"}`}>
                          {slot.booked ? "Booked" : "Available"}
                        </span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>

        {/* Create form */}
        <div className="card" style={{ padding: 24, position: "sticky", top: 80 }}>
          <h3 style={{ fontFamily: "var(--font-display)", fontSize: 18, fontWeight: 400, marginBottom: 20 }}>
            Add New Slot
          </h3>

          {success && <div className="success-msg">{success}</div>}

          <form onSubmit={handleCreate}>
            <div className="form-group">
              <label className="label">Date</label>
              <input
                className="input" type="date"
                value={form.slotDate}
                onChange={e => setForm({ ...form, slotDate: e.target.value })}
                required
              />
            </div>
            <div className="form-group">
              <label className="label">Start Time</label>
              <input
                className="input" type="time"
                value={form.startTime}
                onChange={e => setForm({ ...form, startTime: e.target.value })}
                required
              />
            </div>
            <div className="form-group">
              <label className="label">End Time</label>
              <input
                className="input" type="time"
                value={form.endTime}
                onChange={e => setForm({ ...form, endTime: e.target.value })}
                required
              />
            </div>
            <div className="form-group">
              <label className="label">Mode</label>
              <select className="input" value={form.mode} onChange={e => setForm({ ...form, mode: e.target.value })}>
                <option value="ONLINE">Online</option>
                <option value="OFFLINE">Offline</option>
              </select>
            </div>
            <button type="submit" className="btn btn-primary" style={{ width: "100%" }} disabled={creating}>
              {creating ? <span className="spinner" /> : "Create Slot"}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}