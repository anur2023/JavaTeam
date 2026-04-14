import { useState, useEffect } from 'react';
import { api } from '../services/api';
import { getUserId } from '../utils/auth';

function VendorProducts() {
    const vendorId = getUserId();

    const [products,   setProducts]   = useState([]);
    const [categories, setCategories] = useState([]);
    const [loading,    setLoading]    = useState(true);
    const [error,      setError]      = useState('');
    const [showForm,   setShowForm]   = useState(false);
    const [editing,    setEditing]    = useState(null);
    const [formMsg,    setFormMsg]    = useState({ type: '', text: '' });
    const [submitting, setSubmitting] = useState(false);

    const emptyForm = { name: '', description: '', price: '', stock: '', imageUrl: '', specs: '', categoryId: '' };
    const [form, setForm] = useState(emptyForm);

    useEffect(() => {
        Promise.all([
            api.get('/products'),
            api.get('/categories'),
        ]).then(([prods, cats]) => {
            setProducts((prods || []).filter(p => String(p.vendorId) === String(vendorId)));
            setCategories(cats || []);
        }).catch(err => setError(err.message))
          .finally(() => setLoading(false));
    }, []);

    function openAdd() {
        setEditing(null);
        setForm(emptyForm);
        setFormMsg({ type: '', text: '' });
        setShowForm(true);
    }

    function openEdit(product) {
        setEditing(product.id);
        setForm({
            name:        product.name        || '',
            description: product.description || '',
            price:       product.price       || '',
            stock:       product.stock       || '',
            imageUrl:    product.imageUrl    || '',
            specs:       product.specs       || '',
            categoryId:  product.categoryId  || '',
        });
        setFormMsg({ type: '', text: '' });
        setShowForm(true);
    }

    function handleChange(e) {
        setForm(prev => ({ ...prev, [e.target.name]: e.target.value }));
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setSubmitting(true);
        setFormMsg({ type: '', text: '' });

        const payload = {
            ...form,
            price:      parseFloat(form.price),
            stock:      parseInt(form.stock),
            categoryId: parseInt(form.categoryId),
            vendorId:   parseInt(vendorId),
        };

        try {
            if (editing) {
                const updated = await api.put(`/products/${editing}`, payload);
                setProducts(prev => prev.map(p => p.id === editing ? updated : p));
                setFormMsg({ type: 'success', text: 'Product updated.' });
            } else {
                const created = await api.post('/products', payload);
                setProducts(prev => [...prev, created]);
                setFormMsg({ type: 'success', text: 'Product added.' });
            }
            setForm(emptyForm);
            setEditing(null);
            setTimeout(() => setShowForm(false), 800);
        } catch (err) {
            setFormMsg({ type: 'error', text: err.message });
        } finally {
            setSubmitting(false);
        }
    }

    async function handleDelete(id) {
        if (!window.confirm('Delete this product?')) return;
        try {
            await api.delete(`/products/${id}`);
            setProducts(prev => prev.filter(p => p.id !== id));
        } catch (err) {
            alert(err.message);
        }
    }

    if (loading) return <div className="loading">Loading…</div>;

    return (
        <div>
            <div style={styles.header}>
                <h1 className="page-title" style={{ marginBottom: 0 }}>My Products</h1>
                <button className="btn btn-primary" onClick={openAdd}>+ Add Product</button>
            </div>

            {error && <div className="error-msg">{error}</div>}

            {showForm && (
                <div className="card" style={styles.formCard}>
                    <h2 style={styles.sectionTitle}>{editing ? 'Edit Product' : 'Add New Product'}</h2>
                    <form onSubmit={handleSubmit}>
                        <div className="grid-2">
                            <div className="form-group">
                                <label>Name</label>
                                <input name="name" value={form.name} onChange={handleChange} required />
                            </div>
                            <div className="form-group">
                                <label>Category</label>
                                <select name="categoryId" value={form.categoryId} onChange={handleChange} required>
                                    <option value="">— Select —</option>
                                    {categories.map(c => (
                                        <option key={c.id} value={c.id}>{c.name}</option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-group">
                                <label>Price (₹)</label>
                                <input name="price" type="number" min="0" step="0.01" value={form.price} onChange={handleChange} required />
                            </div>
                            <div className="form-group">
                                <label>Stock</label>
                                <input name="stock" type="number" min="0" value={form.stock} onChange={handleChange} required />
                            </div>
                        </div>
                        <div className="form-group">
                            <label>Description</label>
                            <textarea name="description" rows={2} value={form.description} onChange={handleChange} style={{ resize: 'vertical' }} />
                        </div>
                        <div className="form-group">
                            <label>Image URL</label>
                            <input name="imageUrl" value={form.imageUrl} onChange={handleChange} placeholder="https://…" />
                        </div>
                        <div className="form-group">
                            <label>Specs</label>
                            <textarea name="specs" rows={3} value={form.specs} onChange={handleChange} placeholder="RAM: 8GB&#10;Storage: 256GB" style={{ resize: 'vertical' }} />
                        </div>

                        {formMsg.text && (
                            <div className={formMsg.type === 'success' ? 'success-msg' : 'error-msg'}>
                                {formMsg.text}
                            </div>
                        )}

                        <div style={{ display: 'flex', gap: '10px', marginTop: '10px' }}>
                            <button type="submit" className="btn btn-primary" disabled={submitting}>
                                {submitting ? 'Saving…' : editing ? 'Update' : 'Add Product'}
                            </button>
                            <button type="button" className="btn btn-ghost" onClick={() => setShowForm(false)}>
                                Cancel
                            </button>
                        </div>
                    </form>
                </div>
            )}

            {products.length === 0 ? (
                <div className="empty-state">
                    <p style={{ fontSize: '32px', marginBottom: '8px' }}>📦</p>
                    <p>You have no products yet.</p>
                </div>
            ) : (
                <div style={{ overflowX: 'auto' }}>
                    <table style={styles.table}>
                        <thead>
                            <tr>
                                {['ID', 'Name', 'Category', 'Price', 'Stock', 'Actions'].map(h => (
                                    <th key={h} style={styles.th}>{h}</th>
                                ))}
                            </tr>
                        </thead>
                        <tbody>
                            {products.map(p => (
                                <tr key={p.id}>
                                    <td style={styles.td}>{p.id}</td>
                                    <td style={styles.td}>
                                        <strong>{p.name}</strong>
                                        {p.imageUrl && <img src={p.imageUrl} alt="" style={styles.thumb} />}
                                    </td>
                                    <td style={styles.td}>{p.categoryName || '—'}</td>
                                    <td style={styles.td}>₹{Number(p.price).toLocaleString()}</td>
                                    <td style={styles.td}>
                                        <span className={`badge ${p.stock > 0 ? 'badge-green' : 'badge-red'}`}>
                                            {p.stock}
                                        </span>
                                    </td>
                                    <td style={styles.td}>
                                        <div style={{ display: 'flex', gap: '8px' }}>
                                            <button className="btn btn-ghost" style={styles.actionBtn} onClick={() => openEdit(p)}>Edit</button>
                                            <button className="btn btn-outline" style={{ ...styles.actionBtn, color: '#c8401a', borderColor: '#c8401a' }} onClick={() => handleDelete(p.id)}>Delete</button>
                                        </div>
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
    header: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' },
    formCard: { marginBottom: '24px' },
    sectionTitle: { fontFamily: 'Georgia, serif', fontSize: '16px', marginBottom: '14px', paddingBottom: '10px', borderBottom: '1px solid #e8e6e0' },
    table: { width: '100%', borderCollapse: 'collapse', fontSize: '13px', background: '#fff', border: '1px solid #d0cec8', borderRadius: '4px' },
    th: { textAlign: 'left', padding: '10px 12px', background: '#f5f4f0', borderBottom: '1px solid #d0cec8', fontSize: '11px', fontWeight: '700', textTransform: 'uppercase', letterSpacing: '0.06em', color: '#6b6b66' },
    td: { padding: '10px 12px', borderBottom: '1px solid #f0eeea', verticalAlign: 'middle' },
    thumb: { width: '32px', height: '32px', objectFit: 'cover', marginLeft: '8px', borderRadius: '2px', verticalAlign: 'middle' },
    actionBtn: { fontSize: '11px', padding: '4px 10px' },
};

export default VendorProducts;