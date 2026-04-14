// pages/ProductList.jsx
// Lists all products. Supports category filter + text search.

import { useState, useEffect } from 'react';
import { productService } from '../services/productService';
import ProductCard from '../components/ProductCard';

function ProductList() {
    const [products,   setProducts]   = useState([]);
    const [categories, setCategories] = useState([]);
    const [loading,    setLoading]    = useState(true);
    const [search,     setSearch]     = useState('');
    const [catId,      setCatId]      = useState('');

    // Load categories once
    useEffect(() => {
        productService.getAllCategories()
            .then(setCategories)
            .catch(console.error);
    }, []);

    // Reload products whenever filter changes
    useEffect(() => {
        setLoading(true);

        let fetchPromise;
        if (search.trim()) {
            fetchPromise = productService.searchProducts(search.trim());
        } else if (catId) {
            fetchPromise = productService.getByCategory(catId);
        } else {
            fetchPromise = productService.getAllProducts();
        }

        fetchPromise
            .then(setProducts)
            .catch(console.error)
            .finally(() => setLoading(false));
    }, [search, catId]);

    function handleSearch(e) {
        e.preventDefault();
        // search state already triggers useEffect above
    }

    function clearFilters() {
        setSearch('');
        setCatId('');
    }

    return (
        <div>
            <h1 className="page-title">Products</h1>

            {/* Filter bar */}
            <div style={styles.filterBar}>
                <form onSubmit={handleSearch} style={styles.searchForm}>
                    <input
                        type="text"
                        placeholder="Search products…"
                        value={search}
                        onChange={e => { setSearch(e.target.value); setCatId(''); }}
                        style={styles.searchInput}
                    />
                    <button type="submit" className="btn btn-primary">Search</button>
                </form>

                <select
                    value={catId}
                    onChange={e => { setCatId(e.target.value); setSearch(''); }}
                    style={styles.catSelect}
                >
                    <option value="">All Categories</option>
                    {categories.map(c => (
                        <option key={c.id} value={c.id}>{c.name}</option>
                    ))}
                </select>

                {(search || catId) && (
                    <button onClick={clearFilters} className="btn btn-ghost">Clear</button>
                )}
            </div>

            {/* Results */}
            {loading ? (
                <div className="loading">Loading products…</div>
            ) : products.length === 0 ? (
                <div className="empty-state">
                    <p>No products found.</p>
                    <button onClick={clearFilters} className="btn btn-ghost" style={{ marginTop: '12px' }}>
                        Clear filters
                    </button>
                </div>
            ) : (
                <>
                    <p style={styles.count}>{products.length} product{products.length !== 1 ? 's' : ''}</p>
                    <div className="grid-4">
                        {products.map(p => (
                            <ProductCard key={p.id} product={p} />
                        ))}
                    </div>
                </>
            )}
        </div>
    );
}

const styles = {
    filterBar: {
        display: 'flex',
        gap: '10px',
        marginBottom: '20px',
        flexWrap: 'wrap',
    },
    searchForm: {
        display: 'flex',
        gap: '8px',
        flex: 1,
        minWidth: '260px',
    },
    searchInput: {
        flex: 1,
        padding: '8px 10px',
        border: '1.5px solid #d0cec8',
        borderRadius: '4px',
        fontFamily: "'Courier New', monospace",
        fontSize: '13px',
        background: '#fff',
        color: '#1a1a18',
        outline: 'none',
    },
    catSelect: {
        padding: '8px 10px',
        border: '1.5px solid #d0cec8',
        borderRadius: '4px',
        fontFamily: "'Courier New', monospace",
        fontSize: '13px',
        background: '#fff',
        color: '#1a1a18',
        cursor: 'pointer',
        outline: 'none',
    },
    count: {
        fontSize: '12px',
        color: '#6b6b66',
        marginBottom: '12px',
    },
};

export default ProductList;