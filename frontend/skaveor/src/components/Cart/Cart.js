import React, { useState, useEffect } from "react";
import { Button, Container, Offcanvas, Stack } from "react-bootstrap";
import { useLogin } from "../../context/LoginContext";

import { useShoppingCart } from "../../context/ShoppingCartContext";
import StoreApi from "../../data/store/StoreRestService";
import formatCurrency from "../../utilities/formatCurrency";
import PaymentModal from "../PaymentModal/PaymentModal";
import CartItem from "./CartItem";

const Cart = ({ isOpen }) => {
  const { closeCart, cartItems, getItemQuantity } = useShoppingCart();
  const [items, setItems] = useState([]);
  const [showPaymentModal, setShowPaymentModal] = useState(false);
  const { getAuthHeader } = useLogin();
  useEffect(() => {
    if (isOpen && cartItems.length !== 0) {
      const api = new StoreApi();

      const criteria = {
        search_predicate: {
          condition_operator: "AND",
          search_expressions: [
            {
              field: "id",
              comparison_operator: "IN",
              values: cartItems.map((e) => e.id),
            },
          ],
        },
      };

      async function getItems() {
        setItems(
          await api
            .getItems(JSON.stringify(criteria), getAuthHeader())
            .then((response) => response.json())
            .catch((error) => console.error(error))
        );
      }

      getItems();
    }
  }, [isOpen, cartItems]);

  function calculateTotalAmount() {
    return items.reduce((total, currenItem) => {
      const totalAmount = currenItem.price * getItemQuantity(currenItem.id);
      return total + totalAmount;
    }, 0);
  }

  return (
    <Offcanvas
      show={isOpen}
      placement="end"
      onHide={closeCart}
      className="bg-dark text-light"
    >
      <Offcanvas.Header closeButton>
        <Offcanvas.Title>Cart</Offcanvas.Title>
      </Offcanvas.Header>
      <Offcanvas.Body>
        {cartItems.length !== 0 ? (
          <Stack gap={3}>
            {items.map((e) => (
              <CartItem key={e.id} item={e} quantity={getItemQuantity(e.id)} />
            ))}
            <div className="ms-auto fw-bold fs-5">
              Total:
              {formatCurrency(calculateTotalAmount())}
            </div>

            <Button
              onClick={() => setShowPaymentModal(true)}
              className="rounded-0"
              variant="success"
            >
              Checkout
            </Button>
            <PaymentModal
              show={showPaymentModal}
              onHide={() => setShowPaymentModal(false)}
              totalAmount={calculateTotalAmount()}
            />
          </Stack>
        ) : (
          <div>cart is empty</div>
        )}
      </Offcanvas.Body>
    </Offcanvas>
  );
};

export default Cart;
