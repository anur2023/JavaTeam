// components/ProductCard.jsx
// Displays a single product in a grid. Used in Home and ProductList pages.

import { Link } from 'react-router-dom';

function ProductCard({ product }) {
    return (
        <div style={styles.card}>
            {/* Product image */}
            <div style={styles.imgBox}>
                {product.imageUrl ? (
                    <img src={product.imageUrl} alt={product.name} style={styles.img} />
                ) : (
                    <div style={styles.imgPlaceholder}>📦</div>
                )}
            </div>

            {/* Info */}
            <div style={styles.body}>
                {product.categoryName && (
                    <span style={styles.category}>{product.categoryName}</span>
                )}
                <h3 style={styles.name}>{product.name}</h3>
                <p style={styles.desc}>{product.description?.slice(0, 70)}…</p>

                <div style={styles.footer}>
                    <span style={styles.price}>₹{Number(product.price).toLocaleString()}</span>
                    <Link to={`/products/${product.id}`} style={styles.viewBtn}>
                        View →
                    </Link>
                </div>
            </div>
        </div>
    );
}

const styles = {
    card: {
        background: '#ffffff',
        border: '1px solid #d0cec8',
        borderRadius: '4px',
        overflow: 'hidden',
        display: 'flex',
        flexDirection: 'column',
    },
    imgBox: {
        height: '160px',
        background: '#f0eeea',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        overflow: 'hidden',
        borderBottom: '1px solid #d0cec8',
    },
    img: {
        width: '100%',
        height: '100%',
        objectFit: 'contain',
        padding: '8px',
    },
    imgPlaceholder: {
        fontSize: '48px',
        opacity: 0.4,
    },
    body: {
        padding: '14px',
        display: 'flex',
        flexDirection: 'column',
        gap: '6px',
        flex: 1,
    },
    category: {
        fontSize: '10px',
        fontWeight: '700',
        letterSpacing: '0.08em',
        textTransform: 'uppercase',
        color: '#c8401a',
    },
    name: {
        fontFamily: "Georgia, serif",
        fontSize: '15px',
        fontWeight: '700',
        color: '#1a1a18',
        lineHeight: 1.3,
    },
    desc: {
        fontSize: '12px',
        color: '#6b6b66',
        flex: 1,
    },
    footer: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        marginTop: '8px',
        paddingTop: '8px',
        borderTop: '1px solid #e8e6e0',
    },
    price: {
        fontWeight: '700',
        fontSize: '15px',
        color: '#1a1a18',
    },
    viewBtn: {
        fontSize: '11px',
        fontWeight: '700',
        letterSpacing: '0.04em',
        textTransform: 'uppercase',
        color: '#c8401a',
        textDecoration: 'none',
    },
};

export default ProductCard;