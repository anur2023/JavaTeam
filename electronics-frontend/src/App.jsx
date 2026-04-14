import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import Login from './pages/Login';
import Register from './pages/Register';
import Home from './pages/Home';
import ProductList from './pages/ProductList';
import ProductDetails from './pages/ProductDetails';
import Cart from './pages/Cart';
import Checkout from './pages/Checkout';
import Orders from './pages/Orders';
import ServiceRequest from './pages/ServiceRequest';
import { getToken } from './utils/auth';

// ProtectedRoute: redirects to /login if no token
function ProtectedRoute({ children }) {
    return getToken() ? children : <Navigate to="/login" />;
}

function App() {
    return (
        <BrowserRouter>
            <Navbar />
            <main style={{ padding: '20px', maxWidth: '1100px', margin: '0 auto' }}>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/products" element={<ProductList />} />
                    <Route path="/products/:id" element={<ProductDetails />} />

                    {/* Protected routes */}
                    <Route path="/cart" element={<ProtectedRoute><Cart /></ProtectedRoute>} />
                    <Route path="/checkout" element={<ProtectedRoute><Checkout /></ProtectedRoute>} />
                    <Route path="/orders" element={<ProtectedRoute><Orders /></ProtectedRoute>} />
                    <Route path="/service-request" element={<ProtectedRoute><ServiceRequest /></ProtectedRoute>} />
                </Routes>
            </main>
        </BrowserRouter>
    );
}

export default App;