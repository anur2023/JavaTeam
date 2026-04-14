import { useState, useEffect } from 'react';
import { api } from '../services/api';

function roleBadgeClass(role) {
    if (role === 'ADMIN')  return 'badge-red';
    if (role === 'VENDOR') return 'badge-green';
    return 'badge-gray';
}

function AdminUsers() {
    const [users,   setUsers]   = useState([]);
    const [loading, setLoading] = useState(true);
    const [error,   setError]   = useState('');
    const [search,  setSearch]  = useState('');

    useEffect(() => {
        api.get('/admin/users')
            .then(data => setUsers(Array.isArray(data) ? data : []))
            .catch(err => setError(err.message))
            .finally(() => setLoading(false));
    }, []);

    const filtered = users.filter(u =>
        u.name?.toLowerCase().includes(search.toLowerCase()) ||
        u.email?.toLowerCase().includes(search.toLowerCase()) ||
        u.role?.toLowerCase().includes(search.toLowerCase())
    );

    if (loading) return <div className="loading">Loading users…</div>;

    return (
        <div>
            <h1 className="page-title">All Users</h1>

            {error && <div className="error-msg">{error}</div>}

            <div style={styles.toolbar}>
                <input
                    type="text"
                    placeholder="Search by name, email or role…"
                    value={search}
                    onChange={e => setSearch(e.target.value)}
                    style={styles.searchInput}
                />
                <span style={styles.count}>{filtered.length} user{filtered.length !== 1 ? 's' : ''}</span>
            </div>

            {filtered.length === 0 ? (
                <div className="empty-state">No users found.</div>
            ) : (
                <div style={{ overflowX: 'auto' }}>
                    <table style={styles.table}>
                        <thead>
                            <tr>
                                {['ID', 'Name', 'Email', 'Phone', 'Role'].map(h => (
                                    <th key={h} style={styles.th}>{h}</th>
                                ))}
                            </tr>
                        </thead>
                        <tbody>
                            {filtered.map(user => (
                                <tr key={user.id}>
                                    <td style={styles.td}>{user.id}</td>
                                    <td style={styles.td}><strong>{user.name}</strong></td>
                                    <td style={styles.td}>{user.email}</td>
                                    <td style={styles.td}>{user.phone || '—'}</td>
                                    <td style={styles.td}>
                                        <span className={`badge ${roleBadgeClass(user.role)}`}>{user.role}</span>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}

const styles = {
    toolbar: { display: 'flex', alignItems: 'center', gap: '16px', marginBottom: '16px' },
    searchInput: { padding: '8px 10px', border: '1.5px solid #d0cec8', borderRadius: '4px', fontFamily: "'Courier New', monospace", fontSize: '13px', background: '#fff', color: '#1a1a18', outline: 'none', width: '320px' },
    count: { fontSize: '12px', color: '#6b6b66' },
    table: { width: '100%', borderCollapse: 'collapse', fontSize: '13px', background: '#fff', border: '1px solid #d0cec8', borderRadius: '4px' },
    th: { textAlign: 'left', padding: '10px 12px', background: '#f5f4f0', borderBottom: '1px solid #d0cec8', fontSize: '11px', fontWeight: '700', textTransform: 'uppercase', letterSpacing: '0.06em', color: '#6b6b66' },
    td: { padding: '10px 12px', borderBottom: '1px solid #f0eeea', verticalAlign: 'middle' },
};

export default AdminUsers;