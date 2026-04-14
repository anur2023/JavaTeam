import { Link, useNavigate } from 'react-router-dom';
import { isLoggedIn, removeToken, getRole, getUserName } from '../utils/auth';

function Navbar() {
    const navigate  = useNavigate();
    const loggedIn  = isLoggedIn();
    const role      = getRole();
    const name      = getUserName();

    function handleLogout() {
        removeToken();
        navigate('/login');
    }

    return (
        <nav style={styles.nav}>
            <div style={styles.inner}>
                <Link to="/" style={styles.brand}>⚡ ElectroShop</Link>

                <div style={styles.links}>
                    <Link to="/products" style={styles.link}>Products</Link>

                    {loggedIn ? (
                        <>
                            {/* Customer + Vendor: can use cart */}
                            {(role === 'CUSTOMER' || role === 'VENDOR') && (
                                <Link to="/cart" style={styles.link}>Cart</Link>
                            )}

                            {/* Customer + Vendor: orders */}
                            {(role === 'CUSTOMER' || role === 'VENDOR') && (
                                <Link to="/orders" style={styles.link}>Orders</Link>
                            )}

                            {/* Customer + Vendor: service */}
                            {(role === 'CUSTOMER' || role === 'VENDOR') && (
                                <Link to="/service-request" style={styles.link}>Service</Link>
                            )}

                            {/* Vendor: manage products */}
                            {role === 'VENDOR' && (
                                <Link to="/vendor/products" style={styles.link}>My Products</Link>
                            )}

                            {/* Admin: all orders and users */}
                            {role === 'ADMIN' && (
                                <>
                                    <Link to="/admin/orders" style={styles.link}>All Orders</Link>
                                    <Link to="/admin/users"  style={styles.link}>Users</Link>
                                </>
                            )}

                            {/* Role badge */}
                            <span style={{ ...styles.roleBadge, ...roleBadgeColor(role) }}>
                                {role}
                            </span>

                            {name && (
                                <span style={styles.userName}>{name}</span>
                            )}

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

function roleBadgeColor(role) {
    if (role === 'ADMIN')    return { background: '#532AB7', color: '#fff' };
    if (role === 'VENDOR')   return { background: '#0F6E56', color: '#fff' };
    return { background: '#3d3d3a', color: '#f5f4f0' };
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
        gap: '16px',
    },
    link: {
        color: '#c8c7c0',
        fontSize: '12px',
        fontWeight: '600',
        letterSpacing: '0.06em',
        textTransform: 'uppercase',
        textDecoration: 'none',
    },
    roleBadge: {
        fontSize: '10px',
        fontWeight: '700',
        letterSpacing: '0.06em',
        padding: '2px 8px',
        borderRadius: '3px',
        textTransform: 'uppercase',
    },
    userName: {
        fontSize: '12px',
        color: '#c8c7c0',
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