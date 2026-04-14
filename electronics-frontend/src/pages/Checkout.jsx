// pages/Checkout.jsx
// Confirms order, places it via API, then processes payment.
// Simple "COD / Card" selection - payment is always simulated as SUCCESS.

import { useState, useEffect } from 'react';
import { useNavigate }          from 'react-router-dom';
import { cartService }          from '../services/cartService';
import { orderService }         from '../services/orderService';
import { paymentService }       from '../services/paymentService';
import { getUserId }             from '../utils/auth';

function Checkout() {
    const navigate = useNavigate();
    const userId   = getUserId();

    const [cart,     setCart]     = useState(null);
    const [loading,  setLoading]  = useState(true);
    const [placing,  setPlacing]  = useState(false);
    const [payMode,  setPayMode]  = useState('COD');
    const [error,    setError]    = useState('');

    useEffect(() => {
        if (!userId) return;
        cartService.getCart(userId)
            .then(setCart)
            .catch(err => setError(err.message))
            .finally(() => setLoading(false));
    }, []);

    async function handlePlaceOrder() {
        if (!cart || !cart.items.length) return;
        setPlacing(true);
        setError('');

        try {
            // 1. Build order items from cart
            const items = cart.items.map(i => ({
                productId: i.productId,
                quantity:  i.quantity,
                price:     Number(i.priceAtAdded),
            }));

            // 2. Place order  →  POST /orders
            const order = await orderService.placeOrder(Number(userId), items);

            // 3. Process payment  →  POST /payments
            await paymentService.processPayment(order.id, Number(cart.grandTotal));

            // 4. Clear cart
            await cartService.clearCart(Number(userId));

            // 5. Go to orders page
            navigate('/orders');
        } catch (err) {
            setError(err.message || 'Order failed. Please try again.');
        } finally {
            setPlacing(false);
        }
    }

    if (!userId)  return <div className="empty-state">Please log in again.</div>;
    if (loading)  return <div className="loading">Loading cart…</div>;
    if (!cart || !cart.items?.length)
        return <div className="empty-state">Your cart is empty. <a href="/products">Shop now</a></div>;

    return (
        <div>
            <h1 className="page-title">Checkout</h1>

            <div style={styles.layout}>
                {/* Order summary */}
                <div className="card" style={{ flex: 2 }}>
                    <h2 style={styles.sectionTitle}>Order Summary</h2>

                    {cart.items.map(item => (
                        <div key={item.productId} style={styles.itemRow}>
                            <span style={styles.itemName}>{item.productName}</span>
                            <span style={styles.itemQty}>×{item.quantity}</span>
                            <span style={styles.itemPrice}>
                ₹{Number(item.subTotal).toLocaleString()}
              </span>
                        </div>
                    ))}

                    <div style={styles.total}>
                        <strong>Total</strong>
                        <strong>₹{Number(cart.grandTotal).toLocaleString()}</strong>
                    </div>
                </div>

                {/* Payment section */}
                <div className="card" style={styles.payBox}>
                    <h2 style={styles.sectionTitle}>Payment Method</h2>

                    {['COD', 'CARD', 'UPI'].map(mode => (
                        <label key={mode} style={styles.radioLabel}>
                            <input
                                type="radio"
                                name="payment"
                                value={mode}
                                checked={payMode === mode}
                                onChange={() => setPayMode(mode)}
                                style={{ marginRight: '8px' }}
                            />
                            {mode === 'COD'  && '💵 Cash on Delivery'}
                            {mode === 'CARD' && '💳 Credit / Debit Card'}
                            {mode === 'UPI'  && '📱 UPI'}
                        </label>
                    ))}

                    <p style={styles.simNote}>
                        ⚠️ Payment is simulated — no real charge.
                    </p>

                    {error && <div className="error-msg">{error}</div>}

                    <button
                        className="btn btn-primary"
                        style={{ width: '100%', marginTop: '16px' }}
                        onClick={handlePlaceOrder}
                        disabled={placing}
                    >
                        {placing ? 'Placing Order…' : `Place Order · ₹${Number(cart.grandTotal).toLocaleString()}`}
                    </button>
                </div>
            </div>
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
    sectionTitle: {
        fontFamily: "Georgia, serif",
        fontSize: '16px',
        marginBottom: '14px',
        paddingBottom: '10px',
        borderBottom: '1px solid #e8e6e0',
    },
    itemRow: {
        display: 'flex',
        gap: '10px',
        padding: '8px 0',
        borderBottom: '1px solid #f0eeea',
        fontSize: '13px',
    },
    itemName:  { flex: 1, color: '#1a1a18' },
    itemQty:   { color: '#6b6b66', minWidth: '30px', textAlign: 'center' },
    itemPrice: { fontWeight: '700', minWidth: '80px', textAlign: 'right' },
    total: {
        display: 'flex',
        justifyContent: 'space-between',
        padding: '12px 0 0',
        marginTop: '4px',
        borderTop: '2px solid #1a1a18',
        fontSize: '15px',
    },
    payBox: { flex: 1, minWidth: '240px' },
    radioLabel: {
        display: 'flex',
        alignItems: 'center',
        padding: '10px',
        border: '1px solid #d0cec8',
        borderRadius: '4px',
        marginBottom: '8px',
        cursor: 'pointer',
        fontSize: '13px',
    },
    simNote: {
        fontSize: '11px',
        color: '#6b6b66',
        marginTop: '10px',
        lineHeight: 1.5,
    },
};

export default Checkout;