// pages/Cart.jsx
// Shows cart items, allows quantity change / removal, and leads to Checkout.

import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { cartService } from '../services/cartService';
import { getUserId }   from '../utils/auth';
import CartItem        from '../components/CartItem';

function Cart() {
    const navigate = useNavigate();
    const userId   = getUserId();

    const [cart,    setCart]    = useState(null);
    const [loading, setLoading] = useState(true);
    const [error,   setError]   = useState('');

    function loadCart() {
        if (!userId) return;
        cartService.getCart(userId)
            .then(setCart)
            .catch(err => setError(err.message))
            .finally(() => setLoading(false));
    }

    useEffect(() => { loadCart(); }, []);

    async function handleQuantityChange(productId, newQty) {
        try {
            const updated = await cartService.updateItem(Number(userId), productId, newQty);
            setCart(updated);
        } catch (err) {
            alert(err.message);
        }
    }

    async function handleRemove(productId) {
        try {
            const updated = await cartService.removeItem(Number(userId), productId);
            setCart(updated);
        } catch (err) {
            alert(err.message);
        }
    }

    async function handleClear() {
        if (!window.confirm('Clear entire cart?')) return;
        try {
            await cartService.clearCart(Number(userId));
            setCart(null);
            loadCart();
        } catch (err) {
            alert(err.message);
        }
    }

    if (!userId) {
        return <div className="empty-state">Could not determine user. Please log in again.</div>;
    }
    if (loading) return <div className="loading">Loading cart…</div>;
    if (error)   return <div className="error-msg">{error}</div>;

    const items     = cart?.items || [];
    const grandTotal = cart?.grandTotal || 0;
    const isEmpty   = items.length === 0;

return (
        <div>
            <h1 className="page-title">Your Cart</h1>

            {isEmpty ? (
                <div className="empty-state">
                    <p style={{ fontSize: '32px', marginBottom: '12px' }}>🛒</p>
                    <p>Your cart is empty.</p>
                    <button
                        className="btn btn-primary"
                        onClick={() => navigate('/products')}
                        style={{ marginTop: '16px' }}
                    >Browse Products</button>
                </div>
            ) : (
                <div style={styles.layout}>
                    {/* Items list */}
                    <div className="card" style={{ flex: 2 }}>
                        {items.map(item => (
                            <CartItem
                                key={item.productId}
                                item={item}
                                onQuantityChange={handleQuantityChange}
                                onRemove={handleRemove}
                            />
                        ))}
                        <button
                            onClick={handleClear}
                            className="btn btn-ghost"
                            style={{ marginTop: '14px', fontSize: '11px' }}
                        >
                            Clear Cart
                        </button>
                    </div>

                    {/* Order summary */}
                    <div className="card" style={styles.summary}>
                        <h2 style={styles.summaryTitle}>Order Summary</h2>

                        <div style={styles.summaryRow}>
                            <span>Items ({items.length})</span>
                            <span>₹{Number(grandTotal).toLocaleString()}</span>
                        </div>
                        <div style={styles.summaryRow}>
                            <span>Shipping</span>
                            <span style={{ color: '#2a7a3b' }}>Free</span>
                        </div>
                        <div style={{ ...styles.summaryRow, ...styles.totalRow }}>
                            <strong>Total</strong>
                            <strong>₹{Number(grandTotal).toLocaleString()}</strong>
                        </div>

                        <button
                            className="btn btn-primary"
                            style={{ width: '100%', marginTop: '16px' }}
                            onClick={() => navigate('/checkout')}
                        >
                            Proceed to Checkout
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}

const styles = {
    layout: {
        display: 'flex',
        gap: '20px',
        alignItems: 'flex-start',
        flexWrap: 'wrap',
    },
    summary: {
        flex: 1,
        minWidth: '240px',
    },
    summaryTitle: {
        fontFamily: "Georgia, serif",
        fontSize: '17px',
        marginBottom: '16px',
        paddingBottom: '10px',
        borderBottom: '1px solid #e8e6e0',
    },
    summaryRow: {
        display: 'flex',
        justifyContent: 'space-between',
        fontSize: '13px',
        padding: '6px 0',
        borderBottom: '1px solid #f0eeea',
    },
    totalRow: {
        fontSize: '15px',
        borderBottom: 'none',
        paddingTop: '10px',
        marginTop: '4px',
        borderTop: '2px solid #1a1a18',
    },
};

export default Cart;