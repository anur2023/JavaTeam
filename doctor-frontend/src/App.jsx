import { useState } from "react";
import { isLoggedIn, getRole } from "./utils/auth";
import Navbar from "./components/Navbar";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Doctors from "./pages/Doctors";
import MyAppointments from "./pages/MyAppointments";
import DoctorAppointments from "./pages/DoctorAppointments";
import ManageSlots from "./pages/ManageSlots";
import AdminDashboard from "./pages/AdminDashboard";
import './App.css';
import './index.css'

function getInitialPage() {
  if (!isLoggedIn()) return "login";
  const role = getRole();
  if (role === "ADMIN") return "admin";
  if (role === "DOCTOR") return "doctor-appointments";
  return "doctors";
}

export default function App() {
  const [page, setPage] = useState(getInitialPage);

  const navigate = (p) => setPage(p);

  const noNavPages = ["login", "register"];

  const renderPage = () => {
    switch (page) {
      case "login": return <Login onNavigate={navigate} />;
      case "register": return <Register onNavigate={navigate} />;
      case "doctors": return <Doctors />;
      case "my-appointments": return <MyAppointments />;
      case "doctor-appointments": return <DoctorAppointments />;
      case "manage-slots": return <ManageSlots />;
      case "admin": return <AdminDashboard />;
      default: return <Login onNavigate={navigate} />;
    }
  };

  return (
    <>
      {!noNavPages.includes(page) && isLoggedIn() && (
        <Navbar onNavigate={navigate} page={page} />
      )}
      {renderPage()}
    </>
  );
}