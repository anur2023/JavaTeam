// pages/ServiceRequest.jsx
// Lists the user's active warranties and lets them raise a service request.

import { useState, useEffect } from 'react';
import { getUserId }            from '../utils/auth';
import { api }                  from '../services/api';

/* ── tiny inline services (avoid extra files) ─────────────────────────── */
const warrantyService = {
    getByUser: (userId) => api.get(`/warranties/user/${userId}`),
};
const srService = {
    create:    (payload)  => api.post('/service-request', payload),
    getByUser: (userId)   => api.get(`/service-request/user/${userId}`),
};
/* ─────────────────────────────────────────────────────────────────────── */

function statusBadge(status) {
    const map = {
        ACTIVE:     'badge-green',
        EXPIRED:    'badge-red',
        REQUESTED:  'badge-orange',
        IN_PROGRESS:'badge-orange',
        RESOLVED:   'badge-green',
        CLOSED:     'badge-gray',
    };
    return map[status] || 'badge-gray';
}

function ServiceRequest() {
    const userId = getUserId();

    const [warranties,  setWarranties]  = useState([]);
    const [requests,    setRequests]    = useState([]);
    const [loading,     setLoading]     = useState(true);
    const [error,       setError]       = useState('');

    // Form state
    const [warrantyId,  setWarrantyId]  = useState('');
    const [issue,       setIssue]       = useState('');
    const [submitting,  setSubmitting]  = useState(false);
    const [formMsg,     setFormMsg]     = useState({ type: '', text: '' });

    useEffect(() => {
        if (!userId) {
            setError('User not found. Please log in again.');
            setLoading(false);
            return;
        }

        Promise.all([
            warrantyService.getByUser(userId),
            srService.getByUser(userId),
        ])
            .then(([w, r]) => {
                setWarranties(Array.isArray(w) ? w : []);
                setRequests(Array.isArray(r) ? r : []);
            })
            .catch(err => setError(err.message || 'Failed to load data.'))
            .finally(() => setLoading(false));
    }, []);

    async function handleSubmit(e) {
        e.preventDefault();
        if (!warrantyId || !issue.trim()) return;

        setSubmitting(true);
        setFormMsg({ type: '', text: '' });

        try {
            const newReq = await srService.create({
                userId:           Number(userId),
                warrantyId:       Number(warrantyId),
                issueDescription: issue.trim(),
            });

            setRequests(prev => [newReq, ...prev]);
            setIssue('');
            setWarrantyId('');
            setFormMsg({ type: 'success', text: 'Service request submitted successfully!' });
        } catch (err) {
            setFormMsg({ type: 'error', text: err.message || 'Submission failed.' });
        } finally {
            setSubmitting(false);
        }
    }

    const activeWarranties = warranties.filter(w => w.status === 'ACTIVE');

    if (loading) return <div className="loading">Loading…</div>;

    return (
        <div>
            <h1 className="page-title">Service Requests</h1>

            {error && <div className="error-msg" style={{ marginBottom: '20px' }}>{error}</div>}

            <div style={styles.layout}>

                {/* ── Left: raise new request ─────────────────────── */}
                <div className="card" style={styles.formBox}>
                    <h2 style={styles.sectionTitle}>Raise a New Request</h2>

                    {activeWarranties.length === 0 ? (
                        <div className="empty-state" style={{ padding: '30px 0' }}>
                            <p style={{ fontSize: '28px', marginBottom: '8px' }}>🔧</p>
                            <p>No active warranties found.</p>
                            <p style={{ fontSize: '12px', color: '#6b6b66', marginTop: '6px' }}>
                                Warranties are created automatically when you place an order.
                            </p>
                        </div>
                    ) : (
                        <form onSubmit={handleSubmit}>
                            <div className="form-group">
                                <label>Select Warranty</label>
                                <select
                                    value={warrantyId}
                                    onChange={e => setWarrantyId(e.target.value)}
                                    required
                                >
                                    <option value="">— Choose a warranty —</option>
                                    {activeWarranties.map(w => (
                                        <option key={w.id} value={w.id}>
                                            Warranty #{w.id} — Order Item #{w.orderItem?.id ?? '?'}
                                            {' '}(expires {w.expiryDate
                                            ? new Date(w.expiryDate).toLocaleDateString('en-IN', {
                                                day: '2-digit', month: 'short', year: 'numeric'
                                            })
                                            : '?'})
                                        </option>
                                    ))}
                                </select>
                            </div>

                            <div className="form-group">
                                <label>Describe the Issue</label>
                                <textarea
                                    rows={4}
                                    value={issue}
                                    onChange={e => setIssue(e.target.value)}
                                    placeholder="Describe the problem in detail…"
                                    required
                                    style={{ resize: 'vertical' }}
                                />
                            </div>

                            {formMsg.text && (
                                <div className={formMsg.type === 'success' ? 'success-msg' : 'error-msg'}>
                                    {formMsg.text}
                                </div>
                            )}

                            <button
                                type="submit"
                                className="btn btn-primary"
                                style={{ marginTop: '10px' }}
                                disabled={submitting}
                            >
                                {submitting ? 'Submitting…' : 'Submit Request'}
                            </button>
                        </form>
                    )}
                </div>

                {/* ── Right: all warranties ────────────────────────── */}
                <div style={styles.rightCol}>

                    {/* Warranties table */}
                    <div className="card" style={{ marginBottom: '20px' }}>
                        <h2 style={styles.sectionTitle}>My Warranties</h2>

                        {warranties.length === 0 ? (
                            <p style={styles.emptyMsg}>No warranties found.</p>
                        ) : (
                            <table style={styles.table}>
                                <thead>
                                <tr>
                                    <th style={styles.th}>#</th>
                                    <th style={styles.th}>Order Item</th>
                                    <th style={styles.th}>Start</th>
                                    <th style={styles.th}>Expiry</th>
                                    <th style={styles.th}>Status</th>
                                </tr>
                                </thead>
                                <tbody>
                                {warranties.map(w => (
                                    <tr key={w.id}>
                                        <td style={styles.td}>{w.id}</td>
                                        <td style={styles.td}>#{w.orderItem?.id ?? '?'}</td>
                                        <td style={styles.td}>
                                            {w.startDate
                                                ? new Date(w.startDate).toLocaleDateString('en-IN', { day:'2-digit', month:'short', year:'numeric' })
                                                : '—'}
                                        </td>
                                        <td style={styles.td}>
                                            {w.expiryDate
                                                ? new Date(w.expiryDate).toLocaleDateString('en-IN', { day:'2-digit', month:'short', year:'numeric' })
                                                : '—'}
                                        </td>
                                        <td style={styles.td}>
                                                <span className={`badge ${statusBadge(w.status)}`}>
                                                    {w.status}
                                                </span>
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        )}
                    </div>

                    {/* Past service requests */}
                    <div className="card">
                        <h2 style={styles.sectionTitle}>Past Requests</h2>

                        {requests.length === 0 ? (
                            <p style={styles.emptyMsg}>No service requests yet.</p>
                        ) : (
                            <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
                                {requests
                                    .slice()
                                    .sort((a, b) => b.id - a.id)
                                    .map(r => (
                                        <div key={r.id} style={styles.requestRow}>
                                            <div style={{ flex: 1 }}>
                                                <p style={styles.requestTitle}>
                                                    Request #{r.id}
                                                    {' — '}
                                                    <span style={{ fontWeight: 'normal', color: '#6b6b66' }}>
                                                        Warranty #{r.warranty?.id ?? '?'}
                                                    </span>
                                                </p>
                                                <p style={styles.requestDesc}>{r.issueDescription}</p>
                                            </div>
                                            <span className={`badge ${statusBadge(r.status)}`} style={{ alignSelf: 'flex-start' }}>
                                                {r.status}
                                            </span>
                                        </div>
                                    ))}
                            </div>
                        )}
                    </div>

                </div>
            </div>
        </div>
    );
}

const styles = {
    layout: {
        display: 'grid',
        gridTemplateColumns: '1fr 1.4fr',
        gap: '20px',
        alignItems: 'flex-start',
    },
    formBox: { position: 'sticky', top: '72px' },
    rightCol: { display: 'flex', flexDirection: 'column' },
    sectionTitle: {
        fontFamily: "Georgia, serif",
        fontSize: '16px',
        marginBottom: '14px',
        paddingBottom: '10px',
        borderBottom: '1px solid #e8e6e0',
    },
    emptyMsg: { fontSize: '13px', color: '#6b6b66', padding: '16px 0', textAlign: 'center' },
    table: { width: '100%', borderCollapse: 'collapse', fontSize: '12px' },
    th: {
        textAlign: 'left',
        padding: '6px 8px',
        background: '#f5f4f0',
        borderBottom: '1px solid #d0cec8',
        fontSize: '10px',
        fontWeight: '700',
        textTransform: 'uppercase',
        letterSpacing: '0.06em',
        color: '#6b6b66',
    },
    td: {
        padding: '7px 8px',
        borderBottom: '1px solid #f0eeea',
        color: '#1a1a18',
        verticalAlign: 'middle',
    },
    requestRow: {
        display: 'flex',
        gap: '12px',
        alignItems: 'center',
        padding: '10px',
        background: '#fafaf8',
        border: '1px solid #e8e6e0',
        borderRadius: '4px',
    },
    requestTitle: {
        fontWeight: '700',
        fontSize: '13px',
        color: '#1a1a18',
        marginBottom: '3px',
    },
    requestDesc: {
        fontSize: '12px',
        color: '#444',
        lineHeight: 1.5,
    },
};

export default ServiceRequest;