// pages/ProductDetails.jsx
// Shows full product info. User can add to cart (if logged in).

import { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { productService } from '../services/productService';
import { cartService }    from '../services/cartService';
import { getUserId, isLoggedIn } from '../utils/auth';

function ProductDetails() {
    const { id }     = useParams();
    const navigate   = useNavigate();

    const [product,  setProduct]  = useState(null);
    const [loading,  setLoading]  = useState(true);
    const [qty,      setQty]      = useState(1);
    const [adding,   setAdding]   = useState(false);
    const [message,  setMessage]  = useState({ type: '', text: '' });

    useEffect(() => {
        productService.getProductById(id)
            .then(setProduct)
            .catch(console.error)
            .finally(() => setLoading(false));
    }, [id]);

    async function handleAddToCart() {
        if (!isLoggedIn()) {
            navigate('/login');
            return;
        }

        const userId = getUserId();
        if (!userId) {
            setMessage({ type: 'error', text: 'User ID not found. Please log in again.' });
            return;
        }

        setAdding(true);
        setMessage({ type: '', text: '' });

        try {
            await cartService.addItem(Number(userId), product.id, qty);
            setMessage({ type: 'success', text: `Added ${qty} × ${product.name} to cart!` });
        } catch (err) {
            setMessage({ type: 'error', text: err.message || 'Failed to add to cart.' });
        } finally {
            setAdding(false);
        }
    }

    if (loading) return <div className="loading">Loading product…</div>;
    if (!product) return <div className="empty-state">Product not found.</div>;

    const inStock = product.stock > 0;

    return (
        <div>
            {/* Breadcrumb */}
            <p style={styles.breadcrumb}>
                <Link to="/products">Products</Link> › {product.name}
            </p>

            <div style={styles.layout}>
                {/* Image */}
                <div style={styles.imgBox}>
                    {product.imageUrl ? (
                        <img src={product.imageUrl} alt={product.name} style={styles.img} />
                    ) : (
                        <div style={styles.imgPlaceholder}>📦</div>
                    )}
                </div>

                {/* Info panel */}
                <div style={styles.info}>
                    {product.categoryName && (
                        <span style={styles.category}>{product.categoryName}</span>
                    )}
                    <h1 style={styles.name}>{product.name}</h1>
                    <p style={styles.price}>₹{Number(product.price).toLocaleString()}</p>

                    {/* Stock badge */}
                    <span className={inStock ? 'badge badge-green' : 'badge badge-red'}>
            {inStock ? `In Stock (${product.stock})` : 'Out of Stock'}
          </span>

                    <p style={styles.desc}>{product.description}</p>

                    {/* Specs */}
                    {product.specs && (
                        <div style={styles.specs}>
                            <strong style={styles.specsLabel}>Specifications</strong>
                            <pre style={styles.specsText}>{product.specs}</pre>
                        </div>
                    )}

                    {/* Quantity + Add to Cart */}
                    {inStock && (
                        <div style={styles.actions}>
                            <div style={styles.qtyRow}>
                                <label style={styles.qtyLabel}>Qty:</label>
                                <button
                                    style={styles.qtyBtn}
                                    onClick={() => setQty(q => Math.max(1, q - 1))}
                                >−</button>
                                <span style={styles.qtyNum}>{qty}</span>
                                <button
                                    style={styles.qtyBtn}
                                    onClick={() => setQty(q => Math.min(product.stock, q + 1))}
                                >+</button>
                            </div>

                            <button
                                className="btn btn-primary"
                                onClick={handleAddToCart}
                                disabled={adding}
                                style={{ marginTop: '12px' }}
                            >
                                {adding ? 'Adding…' : 'Add to Cart'}
                            </button>

                            {!isLoggedIn() && (
                                <p style={styles.loginHint}>
                                    <Link to="/login">Log in</Link> to add to cart
                                </p>
                            )}
                        </div>
                    )}

                    {/* Feedback message */}
                    {message.text && (
                        <div className={message.type === 'success' ? 'success-msg' : 'error-msg'}>
                            {message.text}
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

const styles = {
    breadcrumb: {
        fontSize: '12px',
        color: '#6b6b66',
        marginBottom: '20px',
    },
    layout: {
        display: 'grid',
        gridTemplateColumns: '1fr 1fr',
        gap: '32px',
        alignItems: 'start',
    },
    imgBox: {
        background: '#f0eeea',
        border: '1px solid #d0cec8',
        borderRadius: '4px',
        height: '340px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        overflow: 'hidden',
    },
    img: {
        maxWidth: '100%',
        maxHeight: '100%',
        objectFit: 'contain',
        padding: '16px',
    },
    imgPlaceholder: { fontSize: '80px', opacity: 0.3 },
    info: { display: 'flex', flexDirection: 'column', gap: '12px' },
    category: {
        fontSize: '10px', fontWeight: '700', letterSpacing: '0.08em',
        textTransform: 'uppercase', color: '#c8401a',
    },
    name: {
        fontFamily: "Georgia, serif",
        fontSize: '26px', fontWeight: '700', lineHeight: 1.2, color: '#1a1a18',
    },
    price: { fontSize: '24px', fontWeight: '700', color: '#1a1a18' },
    desc:  { fontSize: '14px', color: '#444', lineHeight: 1.7 },
    specs: {
        background: '#f5f4f0',
        border: '1px solid #d0cec8',
        borderRadius: '4px',
        padding: '12px',
    },
    specsLabel: { fontSize: '11px', letterSpacing: '0.06em', textTransform: 'uppercase', color: '#6b6b66', display: 'block', marginBottom: '6px' },
    specsText:  { fontSize: '12px', whiteSpace: 'pre-wrap', color: '#1a1a18', fontFamily: "'Courier New', monospace" },
    actions: { display: 'flex', flexDirection: 'column', gap: '0' },
    qtyRow: { display: 'flex', alignItems: 'center', gap: '10px' },
    qtyLabel: { fontSize: '12px', fontWeight: '700', textTransform: 'uppercase', letterSpacing: '0.06em', color: '#6b6b66' },
    qtyBtn: {
        width: '30px', height: '30px', border: '1px solid #d0cec8',
        borderRadius: '2px', background: '#f5f4f0', cursor: 'pointer',
        fontFamily: 'inherit', fontSize: '16px', padding: 0,
    },
    qtyNum: { minWidth: '28px', textAlign: 'center', fontWeight: '700', fontSize: '16px' },
    loginHint: { fontSize: '12px', color: '#6b6b66', marginTop: '8px' },
};

export default ProductDetails;