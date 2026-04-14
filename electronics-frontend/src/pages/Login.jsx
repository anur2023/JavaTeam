// pages/Login.jsx
// Simple login form. On success, saves JWT token and redirects to home.

import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { authService } from '../services/authService';
import { saveUserInfo } from '../utils/auth';

function Login() {
    const navigate = useNavigate();

    const [email,    setEmail]    = useState('');
    const [password, setPassword] = useState('');
    const [error,    setError]    = useState('');
    const [loading,  setLoading]  = useState(false);

    async function handleSubmit(e) {
        e.preventDefault();
        setError('');
        setLoading(true);

        try {
            // Call POST /auth/login
            const data = await authService.login(email, password);

            // data = { token: '...', role: 'CUSTOMER' }
            // Note: backend doesn't return userId in login response,
            // so we save what we can. userId can be fetched later if needed.
            saveUserInfo(data.token, data.role);

            navigate('/');
        } catch (err) {
            setError(err.message || 'Login failed. Check your credentials.');
        } finally {
            setLoading(false);
        }
    }

    return (
        <div style={styles.wrapper}>
            <div className="card" style={styles.box}>
                <h1 style={styles.title}>Sign In</h1>
                <p style={styles.sub}>Access your ElectroShop account</p>

                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Email</label>
                        <input
                            type="email"
                            value={email}
                            onChange={e => setEmail(e.target.value)}
                            placeholder="you@email.com"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Password</label>
                        <input
                            type="password"
                            value={password}
                            onChange={e => setPassword(e.target.value)}
                            placeholder="••••••••"
                            required
                        />
                    </div>

                    {error && <div className="error-msg">{error}</div>}

                    <button
                        type="submit"
                        className="btn btn-primary"
                        style={{ width: '100%', marginTop: '8px' }}
                        disabled={loading}
                    >
                        {loading ? 'Signing in…' : 'Sign In'}
                    </button>
                </form>

                <p style={styles.footer}>
                    No account? <Link to="/register">Register here</Link>
                </p>
            </div>
        </div>
    );
}

const styles = {
    wrapper: {
        display: 'flex',
        justifyContent: 'center',
        paddingTop: '60px',
    },
    box: {
        width: '100%',
        maxWidth: '400px',
    },
    title: {
        fontFamily: "Georgia, serif",
        fontSize: '24px',
        marginBottom: '4px',
    },
    sub: {
        color: '#6b6b66',
        fontSize: '13px',
        marginBottom: '20px',
    },
    footer: {
        marginTop: '16px',
        fontSize: '13px',
        textAlign: 'center',
        color: '#6b6b66',
    },
};

export default Login;