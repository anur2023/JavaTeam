import { useState, useEffect } from 'react';
import { api } from '../services/api';

const STATUS_OPTIONS = ['PENDING', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED'];

function statusBadgeClass(status) {
    const map = { PENDING: 'badge-orange', CONFIRMED: 'badge-green', SHIPPED: 'badge-green', DELIVERED: 'badge-green', CANCELLED: 'badge-red' };
    return map[status] || 'badge-gray';
}

function AdminOrders() {
    const [orders,   setOrders]   = useState([]);
    const [loading,  setLoading]  = useState(true);
    const [error,    setError]    = useState('');
    const [expanded, setExpanded] = useState(null);
    const [updating, setUpdating] = useState(null);

    useEffect(() => {
        api.get('/orders')
            .then(data => setOrders(Array.isArray(data) ? data : []))
            .catch(err => setError(err.message))
            .finally(() => setLoading(false));
    }, []);

    async function handleStatusChange(orderId, newStatus) {
        setUpdating(orderId);
        try {
            const updated = await api.put(`/orders/status/${orderId}?status=${newStatus}`);
            setOrders(prev => prev.map(o => o.id === orderId ? { ...o, status: updated.status } : o));
        } catch (err) {
            alert(err.message);
        } finally {
            setUpdating(null);
        }
    }

    if (loading) return <div className="loading">Loading orders…</div>;

    return (
        <div>
            <h1 className="page-title">All Orders</h1>

            {error && <div className="error-msg">{error}</div>}

            <p style={styles.count}>{orders.length} total order{orders.length !== 1 ? 's' : ''}</p>

            {orders.length === 0 ? (
                <div className="empty-state">No orders yet.</div>
            ) : (
                <div style={{ overflowX: 'auto' }}>
                    <table style={styles.table}>
                        <thead>
                            <tr>
                                {['Order ID', 'User ID', 'Date', 'Amount', 'Status', 'Update Status', 'Details'].map(h => (
                                    <th key={h} style={styles.th}>{h}</th>
                                ))}
                            </tr>
                        </thead>
                        <tbody>
                            {orders
                                .slice()
                                .sort((a, b) => b.id - a.id)
                                .map(order => (
                                    <>
                                        <tr key={order.id}>
                                            <td style={styles.td}>#{order.id}</td>
                                            <td style={styles.td}>{order.userId}</td>
                                            <td style={styles.td}>
                                                {order.createdAt
                                                    ? new Date(order.createdAt).toLocaleDateString('en-IN', { day: '2-digit', month: 'short', year: 'numeric' })
                                                    : '—'}
                                            </td>
                                            <td style={styles.td}>₹{Number(order.totalAmount).toLocaleString()}</td>
                                            <td style={styles.td}>
                                                <span className={`badge ${statusBadgeClass(order.status)}`}>{order.status}</span>
                                            </td>
                                            <td style={styles.td}>
                                                <select
                                                    value={order.status}
                                                    onChange={e => handleStatusChange(order.id, e.target.value)}
                                                    style={styles.select}
                                                    disabled={updating === order.id}
                                                >
                                                    {STATUS_OPTIONS.map(s => (
                                                        <option key={s} value={s}>{s}</option>
                                                    ))}
                                                </select>
                                            </td>
                                            <td style={styles.td}>
                                                <button
                                                    className="btn btn-ghost"
                                                    style={styles.detailBtn}
                                                    onClick={() => setExpanded(expanded === order.id ? null : order.id)}
                                                >
                                                    {expanded === order.id ? 'Hide ▲' : 'Show ▼'}
                                                </button>
                                            </td>
                                        </tr>

                                        {expanded === order.id && (
                                            <tr key={`${order.id}-detail`}>
                                                <td colSpan={7} style={styles.expandedCell}>
                                                    {order.orderItems && order.orderItems.length > 0 ? (
                                                        <table style={styles.innerTable}>
                                                            <thead>
                                                                <tr>
                                                                    {['Item ID', 'Product ID', 'Qty', 'Unit Price', 'Subtotal'].map(h => (
                                                                        <th key={h} style={styles.innerTh}>{h}</th>
                                                                    ))}
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                {order.orderItems.map(item => (
                                                                    <tr key={item.id}>
                                                                        <td style={styles.innerTd}>{item.id}</td>
                                                                        <td style={styles.innerTd}>#{item.productId}</td>
                                                                        <td style={styles.innerTd}>{item.quantity}</td>
                                                                        <td style={styles.innerTd}>₹{Number(item.price).toLocaleString()}</td>
                                                                        <td style={styles.innerTd}>₹{(Number(item.price) * item.quantity).toLocaleString()}</td>
                                                                    </tr>
                                                                ))}
                                                            </tbody>
                                                        </table>
                                                    ) : (
                                                        <p style={{ fontSize: '12px', color: '#6b6b66' }}>No item details.</p>
                                                    )}
                                                </td>
                                            </tr>
                                        )}
                                    </>
                                ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}

const styles = {
    count: { fontSize: '12px', color: '#6b6b66', marginBottom: '12px' },
    table: { width: '100%', borderCollapse: 'collapse', fontSize: '13px', background: '#fff', border: '1px solid #d0cec8', borderRadius: '4px' },
    th: { textAlign: 'left', padding: '10px 12px', background: '#f5f4f0', borderBottom: '1px solid #d0cec8', fontSize: '11px', fontWeight: '700', textTransform: 'uppercase', letterSpacing: '0.06em', color: '#6b6b66', whiteSpace: 'nowrap' },
    td: { padding: '10px 12px', borderBottom: '1px solid #f0eeea', verticalAlign: 'middle' },
    select: { padding: '5px 8px', border: '1.5px solid #d0cec8', borderRadius: '4px', fontFamily: 'inherit', fontSize: '12px', background: '#fff', cursor: 'pointer' },
    detailBtn: { fontSize: '11px', padding: '4px 10px' },
    expandedCell: { padding: '12px 20px', background: '#fafaf8', borderBottom: '2px solid #d0cec8' },
    innerTable: { width: '100%', borderCollapse: 'collapse', fontSize: '12px' },
    innerTh: { textAlign: 'left', padding: '5px 8px', background: '#f0eeea', borderBottom: '1px solid #d0cec8', fontSize: '10px', fontWeight: '700', textTransform: 'uppercase', letterSpacing: '0.06em', color: '#6b6b66' },
    innerTd: { padding: '6px 8px', borderBottom: '1px solid #e8e6e0', color: '#1a1a18' },
};

export default AdminOrders;