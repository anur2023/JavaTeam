// pages/Orders.jsx
// Shows all orders for the logged-in user with status badges and cancel option.

import { useState, useEffect } from 'react';
import { useNavigate, Link }   from 'react-router-dom';
import { orderService }        from '../services/orderService';
import { getUserId }           from '../utils/auth';

function statusBadge(status) {
    const map = {
        PENDING:   'badge-orange',
        CONFIRMED: 'badge-green',
        SHIPPED:   'badge-green',
        DELIVERED: 'badge-green',
        CANCELLED: 'badge-red',
    };
    return map[status] || 'badge-gray';
}

function OrderRow({ order, onCancel }) {
    const [expanded, setExpanded] = useState(false);
    const canCancel = order.status !== 'SHIPPED' &&
        order.status !== 'DELIVERED' &&
        order.status !== 'CANCELLED';

    return (
        <div style={styles.orderCard}>
            {/* Header row */}
            <div style={styles.orderHeader}>
                <div>
                    <span style={styles.orderId}>Order #{order.id}</span>
                    <span style={styles.orderDate}>
                        {order.createdAt
                            ? new Date(order.createdAt).toLocaleDateString('en-IN', {
                                day: '2-digit', month: 'short', year: 'numeric'
                            })
                            : '—'}
                    </span>
                </div>
                <div style={styles.orderMeta}>
                    <span className={`badge ${statusBadge(order.status)}`}>
                        {order.status}
                    </span>
                    <strong style={styles.orderTotal}>
                        ₹{Number(order.totalAmount).toLocaleString()}
                    </strong>
                    <button
                        className="btn btn-ghost"
                        style={styles.toggleBtn}
                        onClick={() => setExpanded(e => !e)}
                    >
                        {expanded ? 'Hide ▲' : 'Details ▼'}
                    </button>
                    {canCancel && (
                        <button
                            className="btn btn-outline"
                            style={styles.cancelBtn}
                            onClick={() => onCancel(order.id)}
                        >
                            Cancel
                        </button>
                    )}
                </div>
            </div>

            {/* Expanded items */}
            {expanded && (
                <div style={styles.itemsBox}>
                    {order.orderItems && order.orderItems.length > 0 ? (
                        <table style={styles.table}>
                            <thead>
                            <tr>
                                <th style={styles.th}>Product ID</th>
                                <th style={styles.th}>Qty</th>
                                <th style={styles.th}>Price</th>
                                <th style={styles.th}>Subtotal</th>
                            </tr>
                            </thead>
                            <tbody>
                            {order.orderItems.map(item => (
                                <tr key={item.id}>
                                    <td style={styles.td}>
                                        <Link to={`/products/${item.productId}`}>
                                            #{item.productId}
                                        </Link>
                                    </td>
                                    <td style={styles.td}>{item.quantity}</td>
                                    <td style={styles.td}>₹{Number(item.price).toLocaleString()}</td>
                                    <td style={styles.td}>
                                        ₹{(Number(item.price) * item.quantity).toLocaleString()}
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    ) : (
                        <p style={{ fontSize: '13px', color: '#6b6b66' }}>No item details available.</p>
                    )}
                </div>
            )}
        </div>
    );
}

function Orders() {
    const navigate = useNavigate();
    const userId   = getUserId();

    const [orders,  setOrders]  = useState([]);
    const [loading, setLoading] = useState(true);
    const [error,   setError]   = useState('');

    useEffect(() => {
        if (!userId) {
            setError('User not found. Please log in again.');
            setLoading(false);
            return;
        }
        orderService.getOrdersByUser(userId)
            .then(data => setOrders(Array.isArray(data) ? data : []))
            .catch(err => setError(err.message || 'Failed to load orders.'))
            .finally(() => setLoading(false));
    }, []);

    async function handleCancel(orderId) {
        if (!window.confirm(`Cancel order #${orderId}?`)) return;
        try {
            const updated = await orderService.cancelOrder(orderId);
            setOrders(prev =>
                prev.map(o => o.id === orderId ? { ...o, status: updated.status } : o)
            );
        } catch (err) {
            alert(err.message || 'Could not cancel order.');
        }
    }

    if (loading) return <div className="loading">Loading orders…</div>;

    return (
        <div>
            <h1 className="page-title">My Orders</h1>

            {error && <div className="error-msg">{error}</div>}

            {!error && orders.length === 0 ? (
                <div className="empty-state">
                    <p style={{ fontSize: '32px', marginBottom: '12px' }}>📦</p>
                    <p>You haven't placed any orders yet.</p>
                    <button
                        className="btn btn-primary"
                        onClick={() => navigate('/products')}
                        style={{ marginTop: '16px' }}
                    >
                        Shop Now
                    </button>
                </div>
            ) : (
                <div style={styles.list}>
                    {orders
                        .slice()
                        .sort((a, b) => b.id - a.id)   // newest first
                        .map(order => (
                            <OrderRow
                                key={order.id}
                                order={order}
                                onCancel={handleCancel}
                            />
                        ))
                    }
                </div>
            )}

            {/* Link to service requests */}
            {orders.length > 0 && (
                <p style={styles.serviceHint}>
                    Need help with a product?{' '}
                    <Link to="/service-request">Raise a service request →</Link>
                </p>
            )}
        </div>
    );
}

const styles = {
    list: {
        display: 'flex',
        flexDirection: 'column',
        gap: '12px',
    },
    orderCard: {
        background: '#ffffff',
        border: '1px solid #d0cec8',
        borderRadius: '4px',
        padding: '16px 20px',
        boxShadow: '0 1px 4px rgba(0,0,0,0.08)',
    },
    orderHeader: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        flexWrap: 'wrap',
        gap: '10px',
    },
    orderId: {
        fontFamily: "Georgia, serif",
        fontWeight: '700',
        fontSize: '15px',
        color: '#1a1a18',
        marginRight: '12px',
    },
    orderDate: {
        fontSize: '12px',
        color: '#6b6b66',
    },
    orderMeta: {
        display: 'flex',
        alignItems: 'center',
        gap: '12px',
        flexWrap: 'wrap',
    },
    orderTotal: {
        fontSize: '15px',
        color: '#1a1a18',
    },
    toggleBtn: {
        fontSize: '11px',
        padding: '4px 10px',
    },
    cancelBtn: {
        fontSize: '11px',
        padding: '4px 10px',
        color: '#c8401a',
        borderColor: '#c8401a',
    },
    itemsBox: {
        marginTop: '14px',
        paddingTop: '14px',
        borderTop: '1px solid #e8e6e0',
        overflowX: 'auto',
    },
    table: {
        width: '100%',
        borderCollapse: 'collapse',
        fontSize: '13px',
    },
    th: {
        textAlign: 'left',
        padding: '6px 10px',
        background: '#f5f4f0',
        borderBottom: '1px solid #d0cec8',
        fontSize: '11px',
        fontWeight: '700',
        textTransform: 'uppercase',
        letterSpacing: '0.06em',
        color: '#6b6b66',
    },
    td: {
        padding: '8px 10px',
        borderBottom: '1px solid #f0eeea',
        color: '#1a1a18',
    },
    serviceHint: {
        marginTop: '24px',
        fontSize: '13px',
        color: '#6b6b66',
        textAlign: 'center',
    },
};

export default Orders;