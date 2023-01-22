import { Button, Stack } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { useShoppingCart } from "../../context/ShoppingCartContext";
import formatCurrency from "../../utilities/formatCurrency";

const CartItem = ({ item: { id, title, price, title_picture }, quantity }) => {
  const { removeFromCart, increaseItemQuantity, decreaseItemQuantity } =
    useShoppingCart();
  const navigate = useNavigate();
  return (
    <Stack direction="horizontal" gap={2} className="d-flex align-items-center">
      <img
        onClick={() => navigate(`/store/items/${id}`)}
        src={title_picture}
        style={{ width: "125px", height: "75px", objectFit: "cover" }}
      />

      <div className="me-auto">
        <div>
          {title}
          {quantity > 1 && (
            <span className="text-muted" style={{ fontSize: ".65rem" }}>
              {` x${quantity}`}
            </span>
          )}
        </div>
        <div className="text-muted" style={{ fontSize: ".75rem" }}>
          {price !== null ? formatCurrency(price) : "Free"}
        </div>
      </div>

      <div>{formatCurrency(price * quantity)}</div>

      <Button
        className="rounded-0"
        variant="outline-danger"
        size="sm"
        onClick={() => removeFromCart(id)}
      >
        &times;
      </Button>
    </Stack>
  );
};

export default CartItem;
