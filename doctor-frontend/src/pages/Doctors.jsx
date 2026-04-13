import { useState, useEffect } from "react";
import { getAllDoctors, getSpecialties, getDoctorsBySpecialty, getAvailableSlots, bookAppointment } from "../services/api";

export default function Doctors() {
  const [doctors, setDoctors] = useState([]);
  const [specialties, setSpecialties] = useState([]);
  const [selectedSpecialty, setSelectedSpecialty] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [selectedDoctor, setSelectedDoctor] = useState(null);
  const [slots, setSlots] = useState([]);
  const [slotsLoading, setSlotsLoading] = useState(false);
  const [booking, setBooking] = useState(false);
  const [bookingSuccess, setBookingSuccess] = useState("");
  const [bookingError, setBookingError] = useState("");
  const [notes, setNotes] = useState("");

  useEffect(() => {
    loadDoctors();
    loadSpecialties();
  }, []);

  const loadDoctors = async () => {
    try {
      setLoading(true);
      const data = await getAllDoctors();
      setDoctors(data);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  const loadSpecialties = async () => {
    try {
      const data = await getSpecialties();
      setSpecialties(data);
    } catch {}
  };

  const handleSpecialtyChange = async (id) => {
    setSelectedSpecialty(id);
    if (!id) { loadDoctors(); return; }
    try {
      setLoading(true);
      const data = await getDoctorsBySpecialty(id);
      setDoctors(data);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  const openDoctor = async (doc) => {
    setSelectedDoctor(doc);
    setSlots([]);
    setBookingSuccess("");
    setBookingError("");
    setNotes("");
    setSlotsLoading(true);
    try {
      const data = await getAvailableSlots(doc.doctorId);
      setSlots(data);
    } catch {}
    setSlotsLoading(false);
  };

  const handleBook = async (slotId) => {
    setBooking(true);
    setBookingError("");
    try {
      await bookAppointment({ slotId, notes });
      setBookingSuccess("Appointment booked successfully!");
      const data = await getAvailableSlots(selectedDoctor.doctorId);
      setSlots(data);
    } catch (e) {
      setBookingError(e.message);
    } finally {
      setBooking(false);
    }
  };

  return (
    <div className="page-wrapper">
      <div className="flex-between" style={{ marginBottom: 28 }}>
        <div>
          <h1 className="page-title">Find Doctors</h1>
          <p className="page-subtitle">Browse and book available appointments</p>
        </div>
        <select
          className="input"
          style={{ width: 220 }}
          value={selectedSpecialty}
          onChange={e => handleSpecialtyChange(e.target.value)}
        >
          <option value="">All Specialties</option>
          {specialties.map(s => (
            <option key={s.specialtyId} value={s.specialtyId}>{s.name}</option>
          ))}
        </select>
      </div>

      {error && <div className="error-msg">{error}</div>}

      {loading ? (
        <div className="loading-screen"><span className="spinner" /> Loading doctors...</div>
      ) : doctors.length === 0 ? (
        <div className="empty-state">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
          <span>No doctors found</span>
        </div>
      ) : (
        <div className="grid-3">
          {doctors.map(doc => (
            <div key={doc.doctorId} className="card" style={{ padding: 24, cursor: "pointer" }} onClick={() => openDoctor(doc)}>
              <div style={{ display: "flex", alignItems: "center", gap: 14, marginBottom: 16 }}>
                <div style={{
                  width: 48, height: 48, borderRadius: "50%",
                  background: "var(--accent-bg)", color: "var(--accent)",
                  display: "flex", alignItems: "center", justifyContent: "center",
                  fontFamily: "var(--font-display)", fontSize: 20,
                }}>
                  {doc.userId?.toString().slice(-1) || "D"}
                </div>
                <div>
                  <div style={{ fontWeight: 500, fontSize: 15 }}>Doctor #{doc.doctorId}</div>
                  <div style={{ fontSize: 13, color: "var(--text-muted)" }}>User ID: {doc.userId}</div>
                </div>
              </div>

              <div style={{ display: "flex", flexDirection: "column", gap: 8 }}>
                <div style={{ display: "flex", justifyContent: "space-between", fontSize: 13 }}>
                  <span style={{ color: "var(--text-soft)" }}>Experience</span>
                  <span style={{ fontWeight: 500 }}>{doc.experienceYears} yrs</span>
                </div>
                <div style={{ display: "flex", justifyContent: "space-between", fontSize: 13 }}>
                  <span style={{ color: "var(--text-soft)" }}>Online fee</span>
                  <span style={{ fontWeight: 500, color: "var(--accent)" }}>₹{doc.consultationFeeOnline}</span>
                </div>
                <div style={{ display: "flex", justifyContent: "space-between", fontSize: 13 }}>
                  <span style={{ color: "var(--text-soft)" }}>Offline fee</span>
                  <span style={{ fontWeight: 500 }}>₹{doc.consultationFeeOffline}</span>
                </div>
              </div>

              <button className="btn btn-primary" style={{ width: "100%", marginTop: 16 }}>
                Book Appointment
              </button>
            </div>
          ))}
        </div>
      )}

      {/* Slot modal */}
      {selectedDoctor && (
        <div style={{
          position: "fixed", inset: 0, background: "rgba(0,0,0,0.4)",
          display: "flex", alignItems: "center", justifyContent: "center",
          zIndex: 200, padding: 24,
        }} onClick={e => e.target === e.currentTarget && setSelectedDoctor(null)}>
          <div className="card" style={{ width: "100%", maxWidth: 560, maxHeight: "80vh", overflow: "auto", padding: 32 }}>
            <div className="flex-between" style={{ marginBottom: 24 }}>
              <h3 style={{ fontFamily: "var(--font-display)", fontSize: 22, fontWeight: 400 }}>
                Book Appointment
              </h3>
              <button onClick={() => setSelectedDoctor(null)} style={{ background: "none", fontSize: 20, color: "var(--text-soft)" }}>✕</button>
            </div>

            <div style={{ background: "var(--accent-bg)", borderRadius: "var(--radius-sm)", padding: "12px 16px", marginBottom: 20, fontSize: 13 }}>
              <strong>Doctor #{selectedDoctor.doctorId}</strong> · {selectedDoctor.experienceYears} years experience
              <span style={{ marginLeft: 12, color: "var(--accent)" }}>Online: ₹{selectedDoctor.consultationFeeOnline}</span>
            </div>

            {bookingSuccess && <div className="success-msg">{bookingSuccess}</div>}
            {bookingError && <div className="error-msg">{bookingError}</div>}

            <div className="form-group">
              <label className="label">Notes (optional)</label>
              <input className="input" placeholder="Describe your symptoms..." value={notes} onChange={e => setNotes(e.target.value)} />
            </div>

            <div style={{ fontSize: 13, fontWeight: 500, color: "var(--text-soft)", marginBottom: 12, textTransform: "uppercase", letterSpacing: "0.06em" }}>
              Available Slots
            </div>

            {slotsLoading ? (
              <div className="loading-screen" style={{ height: 100 }}><span className="spinner" /></div>
            ) : slots.length === 0 ? (
              <div className="empty-state" style={{ padding: "32px 0" }}>No available slots</div>
            ) : (
              <div style={{ display: "flex", flexDirection: "column", gap: 10 }}>
                {slots.map(slot => (
                  <div key={slot.id} style={{
                    display: "flex", alignItems: "center", justifyContent: "space-between",
                    padding: "12px 16px", border: "1px solid var(--border)",
                    borderRadius: "var(--radius-sm)", background: "var(--bg)",
                  }}>
                    <div>
                      <div style={{ fontSize: 14, fontWeight: 500 }}>{slot.slotDate}</div>
                      <div style={{ fontSize: 12, color: "var(--text-soft)" }}>
                        {slot.startTime} – {slot.endTime}
                        <span className={`badge badge-${slot.mode === "ONLINE" ? "blue" : "amber"}`} style={{ marginLeft: 8 }}>{slot.mode}</span>
                      </div>
                    </div>
                    <button
                      className="btn btn-primary btn-sm"
                      onClick={() => handleBook(slot.id)}
                      disabled={booking}
                    >
                      {booking ? <span className="spinner" style={{ width: 14, height: 14 }} /> : "Book"}
                    </button>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}