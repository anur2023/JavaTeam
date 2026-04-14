// components/Navbar.jsx
// Top navigation bar. Shows different links based on login state.

import { Link, useNavigate } from 'react-router-dom';
import { isLoggedIn, removeToken } from '../utils/auth';

function Navbar() {
    const navigate = useNavigate();
    const loggedIn = isLoggedIn();

    function handleLogout() {
        removeToken();
        navigate('/login');
    }

    return (
        <nav style={styles.nav}>
            <div style={styles.inner}>
                {/* Brand */}
                <Link to="/" style={styles.brand}>⚡ ElectroShop</Link>

                {/* Links */}
                <div style={styles.links}>
                    <Link to="/products" style={styles.link}>Products</Link>

                    {loggedIn ? (
                        <>
                            <Link to="/cart"    style={styles.link}>Cart</Link>
                            <Link to="/orders"  style={styles.link}>Orders</Link>
                            <Link to="/service-request" style={styles.link}>Service</Link>
                            <button onClick={handleLogout} style={styles.logoutBtn}>Logout</button>
                        </>
                    ) : (
                        <>
                            <Link to="/login"    style={styles.link}>Login</Link>
                            <Link to="/register" style={styles.link}>Register</Link>
                        </>
                    )}
                </div>
            </div>
        </nav>
    );
}

const styles = {
    nav: {
        background: '#1a1a18',
        color: '#f5f4f0',
        borderBottom: '3px solid #c8401a',
        position: 'sticky',
        top: 0,
        zIndex: 100,
    },
    inner: {
        maxWidth: '1100px',
        margin: '0 auto',
        padding: '0 20px',
        height: '52px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
    },
    brand: {
        color: '#f5f4f0',
        fontFamily: "Georgia, serif",
        fontSize: '18px',
        fontWeight: '700',
        letterSpacing: '-0.01em',
        textDecoration: 'none',
    },
    links: {
        display: 'flex',
        alignItems: 'center',
        gap: '20px',
    },
    link: {
        color: '#c8c7c0',
        fontSize: '12px',
        fontWeight: '600',
        letterSpacing: '0.06em',
        textTransform: 'uppercase',
        textDecoration: 'none',
    },
    logoutBtn: {
        background: 'transparent',
        color: '#c8401a',
        border: '1px solid #c8401a',
        borderRadius: '3px',
        padding: '4px 12px',
        fontSize: '11px',
        fontWeight: '700',
        letterSpacing: '0.06em',
        textTransform: 'uppercase',
        cursor: 'pointer',
        fontFamily: "'Courier New', monospace",
    },
};

export default Navbar;