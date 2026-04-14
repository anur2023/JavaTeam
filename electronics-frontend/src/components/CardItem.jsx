// components/CartItem.jsx
// Displays one row in the cart with quantity controls.

function CartItem({ item, onQuantityChange, onRemove }) {
    return (
        <div style={styles.row}>
            {/* Product name + category */}
            <div style={styles.info}>
                <p style={styles.name}>{item.productName}</p>
                <p style={styles.price}>₹{Number(item.priceAtAdded).toLocaleString()} each</p>
            </div>

            {/* Quantity controls */}
            <div style={styles.qty}>
                <button
                    style={styles.qtyBtn}
                    onClick={() => onQuantityChange(item.productId, item.quantity - 1)}
                    disabled={item.quantity <= 1}
                >−</button>
                <span style={styles.qtyNum}>{item.quantity}</span>
                <button
                    style={styles.qtyBtn}
                    onClick={() => onQuantityChange(item.productId, item.quantity + 1)}
                >+</button>
            </div>

            {/* Subtotal */}
            <div style={styles.subtotal}>
                ₹{Number(item.subTotal).toLocaleString()}
            </div>

            {/* Remove */}
            <button
                style={styles.removeBtn}
                onClick={() => onRemove(item.productId)}
            >✕</button>
        </div>
    );
}

const styles = {
    row: {
        display: 'flex',
        alignItems: 'center',
        gap: '16px',
        padding: '12px 0',
        borderBottom: '1px solid #e8e6e0',
    },
    info: {
        flex: 1,
    },
    name: {
        fontFamily: "Georgia, serif",
        fontWeight: '700',
        fontSize: '14px',
        color: '#1a1a18',
    },
    price: {
        fontSize: '12px',
        color: '#6b6b66',
        marginTop: '2px',
    },
    qty: {
        display: 'flex',
        alignItems: 'center',
        gap: '8px',
    },
    qtyBtn: {
        width: '28px',
        height: '28px',
        border: '1px solid #d0cec8',
        borderRadius: '2px',
        background: '#f5f4f0',
        cursor: 'pointer',
        fontFamily: "inherit",
        fontSize: '16px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        padding: 0,
    },
    qtyNum: {
        minWidth: '24px',
        textAlign: 'center',
        fontWeight: '700',
        fontSize: '14px',
    },
    subtotal: {
        fontWeight: '700',
        minWidth: '80px',
        textAlign: 'right',
        fontSize: '14px',
    },
    removeBtn: {
        background: 'transparent',
        border: 'none',
        color: '#c8401a',
        cursor: 'pointer',
        fontSize: '14px',
        padding: '4px',
        fontFamily: 'inherit',
    },
};

export default CartItem;