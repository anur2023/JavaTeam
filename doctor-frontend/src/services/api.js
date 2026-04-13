const BASE_URL = "http://localhost:5000";

const authHeaders = () => ({
  "Content-Type": "application/json",
  Authorization: `Bearer ${localStorage.getItem("token")}`,
});

const req = async (url, options = {}) => {
  const res = await fetch(`${BASE_URL}${url}`, options);
  if (!res.ok) {
    const err = await res.json().catch(() => ({ message: res.statusText }));
    throw new Error(err.message || "Request failed");
  }
  return res.json();
};

// 🔐 AUTH
export const login = (data) =>
  req("/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

export const register = (data) =>
  req("/auth/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

// 👤 USER
export const getProfile = () =>
  req("/users/profile", { headers: authHeaders() });

// 👨‍⚕️ DOCTORS
export const getAllDoctors = () =>
  req("/doctors", { headers: authHeaders() });

export const getDoctorById = (id) =>
  req(`/doctors/${id}`, { headers: authHeaders() });

// ✅ NEW FUNCTION (FIXED ERROR)
export const getDoctorByUserId = (userId) =>
  req(`/doctors/user/${userId}`, { headers: authHeaders() });

// 🏥 SPECIALTIES
export const getSpecialties = () =>
  req("/specialties", { headers: authHeaders() });

export const getDoctorsBySpecialty = (id) =>
  req(`/doctors/specialty/${id}`, { headers: authHeaders() });

// ⏰ SLOTS
export const getAvailableSlots = (doctorId) =>
  req(`/slots/${doctorId}/available`, { headers: authHeaders() });

export const getAllSlots = (doctorId) =>
  req(`/slots/${doctorId}`, { headers: authHeaders() });

export const createSlot = (data) =>
  req("/slots", {
    method: "POST",
    headers: authHeaders(),
    body: JSON.stringify(data),
  });

// 📅 APPOINTMENTS
export const bookAppointment = (data) =>
  req("/appointments", {
    method: "POST",
    headers: authHeaders(),
    body: JSON.stringify(data),
  });

export const getUserAppointments = () =>
  req("/appointments/user", { headers: authHeaders() });

export const getDoctorAppointments = () =>
  req("/doctor-appointments/my", { headers: authHeaders() });

export const updateAppointmentStatus = (id, status) =>
  req(`/doctor-appointments/${id}/status`, {
    method: "PUT",
    headers: authHeaders(),
    body: JSON.stringify({ status }),
  });

// 🛠️ ADMIN
export const getAdminAppointments = () =>
  req("/admin/appointments", { headers: authHeaders() });

export const getDailyReport = () =>
  req("/admin/reports/daily", { headers: authHeaders() });

export const getRevenueReport = () =>
  req("/admin/revenue", { headers: authHeaders() });

export const getSpecialtyStats = () =>
  req("/admin/specialty-stats", { headers: authHeaders() });

// ➕ CREATE
export const createDoctor = (data) =>
  req("/doctors", {
    method: "POST",
    headers: authHeaders(),
    body: JSON.stringify(data),
  });

export const createSpecialty = (data) =>
  req("/specialties", {
    method: "POST",
    headers: authHeaders(),
    body: JSON.stringify(data),
  });

export const addDoctorSpecialty = (doctorId, specialtyId) =>
  req(`/doctor-specialties?doctorId=${doctorId}&specialtyId=${specialtyId}`, {
    method: "POST",
    headers: authHeaders(),
  });