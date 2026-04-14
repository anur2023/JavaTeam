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
import VendorProducts from './pages/VendorProducts';
import AdminOrders from './pages/AdminOrders';
import AdminUsers from './pages/AdminUsers';
import { getToken, getRole } from './utils/auth';

function ProtectedRoute({ children, roles }) {
    if (!getToken()) return <Navigate to="/login" />;
    if (roles && !roles.includes(getRole())) {
        return <div style={{ padding: '40px', textAlign: 'center', color: '#c8401a' }}>
            Access denied. You do not have permission to view this page.
        </div>;
    }
    return children;
}

function App() {
    return (
        <BrowserRouter>
            <Navbar />
            <main style={{ padding: '20px', maxWidth: '1100px', margin: '0 auto' }}>
                <Routes>
                    <Route path="/"          element={<Home />} />
                    <Route path="/login"     element={<Login />} />
                    <Route path="/register"  element={<Register />} />
                    <Route path="/products"  element={<ProductList />} />
                    <Route path="/products/:id" element={<ProductDetails />} />

                    {/* Customer + Vendor */}
                    <Route path="/cart" element={
                        <ProtectedRoute roles={['CUSTOMER', 'VENDOR', 'ADMIN']}>
                            <Cart />
                        </ProtectedRoute>
                    } />
                    <Route path="/checkout" element={
                        <ProtectedRoute roles={['CUSTOMER', 'VENDOR', 'ADMIN']}>
                            <Checkout />
                        </ProtectedRoute>
                    } />
                    <Route path="/orders" element={
                        <ProtectedRoute roles={['CUSTOMER', 'VENDOR', 'ADMIN']}>
                            <Orders />
                        </ProtectedRoute>
                    } />
                    <Route path="/service-request" element={
                        <ProtectedRoute roles={['CUSTOMER', 'VENDOR', 'ADMIN']}>
                            <ServiceRequest />
                        </ProtectedRoute>
                    } />

                    {/* Vendor only */}
                    <Route path="/vendor/products" element={
                        <ProtectedRoute roles={['VENDOR', 'ADMIN']}>
                            <VendorProducts />
                        </ProtectedRoute>
                    } />

                    {/* Admin only */}
                    <Route path="/admin/orders" element={
                        <ProtectedRoute roles={['ADMIN']}>
                            <AdminOrders />
                        </ProtectedRoute>
                    } />
                    <Route path="/admin/users" element={
                        <ProtectedRoute roles={['ADMIN']}>
                            <AdminUsers />
                        </ProtectedRoute>
                    } />
                </Routes>
            </main>
        </BrowserRouter>
    );
}

export default App;