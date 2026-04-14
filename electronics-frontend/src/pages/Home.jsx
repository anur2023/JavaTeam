// pages/Home.jsx
// Landing page. Shows a hero banner + featured products (first 8).

import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { productService } from '../services/productService';
import ProductCard from '../components/ProductCard';

function Home() {
    const [products, setProducts] = useState([]);
    const [loading,  setLoading]  = useState(true);

    useEffect(() => {
        productService.getAllProducts()
            .then(data => setProducts(data.slice(0, 8)))   // show first 8
            .catch(err => console.error('Home load error:', err))
            .finally(() => setLoading(false));
    }, []);

    return (
        <div>
            {/* Hero Banner */}
            <div style={styles.hero}>
                <div style={styles.heroText}>
                    <p style={styles.heroTag}>Electronics Store</p>
                    <h1 style={styles.heroTitle}>Quality Tech,<br/>Fair Prices</h1>
                    <p style={styles.heroSub}>
                        Laptops, mobiles, accessories & more — with 1-year warranty on every item.
                    </p>
                    <Link to="/products" className="btn btn-primary" style={{ marginTop: '16px', display: 'inline-block' }}>
                        Browse All Products
                    </Link>
                </div>
            </div>

            {/* Featured Products */}
            <section style={{ marginTop: '40px' }}>
                <div style={styles.sectionHead}>
                    <h2 style={styles.sectionTitle}>Featured Products</h2>
                    <Link to="/products" style={styles.viewAll}>View all →</Link>
                </div>

                {loading ? (
                    <div className="loading">Loading products…</div>
                ) : products.length === 0 ? (
                    <div className="empty-state">No products found.</div>
                ) : (
                    <div className="grid-4">
                        {products.map(p => (
                            <ProductCard key={p.id} product={p} />
                        ))}
                    </div>
                )}
            </section>

            {/* Feature highlights */}
            <section style={styles.features}>
                {[
                    { icon: '🔒', title: '1-Year Warranty',     desc: 'All products covered' },
                    { icon: '🚚', title: 'Fast Delivery',        desc: 'Pan-India shipping'   },
                    { icon: '↩️',  title: 'Easy Returns',        desc: '7-day return policy'  },
                    { icon: '🛠️', title: 'Service Support',     desc: 'Online service requests' },
                ].map(f => (
                    <div key={f.title} style={styles.featureCard}>
                        <span style={styles.featureIcon}>{f.icon}</span>
                        <strong style={styles.featureTitle}>{f.title}</strong>
                        <span style={styles.featureDesc}>{f.desc}</span>
                    </div>
                ))}
            </section>
        </div>
    );
}

const styles = {
    hero: {
        background: '#1a1a18',
        color: '#f5f4f0',
        borderRadius: '4px',
        padding: '48px 40px',
        marginTop: '8px',
        borderLeft: '6px solid #c8401a',
    },
    heroTag: {
        fontSize: '11px',
        fontWeight: '700',
        letterSpacing: '0.1em',
        textTransform: 'uppercase',
        color: '#c8401a',
        marginBottom: '10px',
    },
    heroTitle: {
        fontFamily: "Georgia, serif",
        fontSize: '42px',
        fontWeight: '700',
        lineHeight: 1.15,
        marginBottom: '14px',
    },
    heroSub: {
        fontSize: '15px',
        color: '#c8c7c0',
        maxWidth: '480px',
        lineHeight: 1.6,
    },
    sectionHead: {
        display: 'flex',
        alignItems: 'baseline',
        justifyContent: 'space-between',
        marginBottom: '16px',
    },
    sectionTitle: {
        fontFamily: "Georgia, serif",
        fontSize: '20px',
    },
    viewAll: {
        fontSize: '12px',
        fontWeight: '700',
        letterSpacing: '0.04em',
        textTransform: 'uppercase',
        color: '#c8401a',
    },
    features: {
        display: 'grid',
        gridTemplateColumns: 'repeat(4, 1fr)',
        gap: '12px',
        marginTop: '48px',
        paddingTop: '32px',
        borderTop: '1px solid #d0cec8',
    },
    featureCard: {
        display: 'flex',
        flexDirection: 'column',
        gap: '4px',
        padding: '16px',
        background: '#ffffff',
        border: '1px solid #d0cec8',
        borderRadius: '4px',
    },
    featureIcon:  { fontSize: '22px' },
    featureTitle: { fontSize: '13px', fontWeight: '700', color: '#1a1a18', marginTop: '4px' },
    featureDesc:  { fontSize: '12px', color: '#6b6b66' },
};

export default Home;