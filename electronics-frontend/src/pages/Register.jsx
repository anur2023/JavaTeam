// pages/Register.jsx
// Registration form. On success, redirects to login.

import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { authService } from '../services/authService';

function Register() {
    const navigate = useNavigate();

    const [form, setForm] = useState({
        name: '', email: '', password: '', phone: '', role: 'CUSTOMER',
    });
    const [error,   setError]   = useState('');
    const [success, setSuccess] = useState('');
    const [loading, setLoading] = useState(false);

    function handleChange(e) {
        setForm(prev => ({ ...prev, [e.target.name]: e.target.value }));
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setError('');
        setSuccess('');
        setLoading(true);

        try {
            await authService.register(
                form.name, form.email, form.password, form.role, form.phone
            );
            setSuccess('Account created! Redirecting to login…');
            setTimeout(() => navigate('/login'), 1500);
        } catch (err) {
            setError(err.message || 'Registration failed.');
        } finally {
            setLoading(false);
        }
    }

    return (
        <div style={styles.wrapper}>
            <div className="card" style={styles.box}>
                <h1 style={styles.title}>Create Account</h1>
                <p style={styles.sub}>Join ElectroShop today</p>

                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Full Name</label>
                        <input
                            name="name"
                            value={form.name}
                            onChange={handleChange}
                            placeholder="John Doe"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Email</label>
                        <input
                            name="email"
                            type="email"
                            value={form.email}
                            onChange={handleChange}
                            placeholder="you@email.com"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Password</label>
                        <input
                            name="password"
                            type="password"
                            value={form.password}
                            onChange={handleChange}
                            placeholder="Min. 6 characters"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Phone (optional)</label>
                        <input
                            name="phone"
                            value={form.phone}
                            onChange={handleChange}
                            placeholder="9876543210"
                        />
                    </div>

                    <div className="form-group">
                        <label>Account Type</label>
                        <select name="role" value={form.role} onChange={handleChange}>
                            <option value="CUSTOMER">Customer</option>
                            <option value="VENDOR">Vendor</option>
                        </select>
                    </div>

                    {error   && <div className="error-msg">{error}</div>}
                    {success && <div className="success-msg">{success}</div>}

                    <button
                        type="submit"
                        className="btn btn-primary"
                        style={{ width: '100%', marginTop: '8px' }}
                        disabled={loading}
                    >
                        {loading ? 'Creating…' : 'Create Account'}
                    </button>
                </form>

                <p style={styles.footer}>
                    Already registered? <Link to="/login">Sign in</Link>
                </p>
            </div>
        </div>
    );
}

const styles = {
    wrapper: { display: 'flex', justifyContent: 'center', paddingTop: '40px' },
    box:     { width: '100%', maxWidth: '420px' },
    title:   { fontFamily: "Georgia, serif", fontSize: '24px', marginBottom: '4px' },
    sub:     { color: '#6b6b66', fontSize: '13px', marginBottom: '20px' },
    footer:  { marginTop: '16px', fontSize: '13px', textAlign: 'center', color: '#6b6b66' },
};

export default Register;