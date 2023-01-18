import React, { useState } from "react";
import { Button, Form, Modal } from "react-bootstrap";
import formatCurrency from "../../utilities/formatCurrency";
import OrderApi from "../../data/OrderRestApi";
import { useNavigate } from "react-router-dom";
import { useShoppingCart } from "../../context/ShoppingCartContext";
import PaymentFailed from "./PaymentFailed";
export default function PaymentModal({ show, onHide, totalAmount }) {
  const { cartItems, closeCart, clearCart } = useShoppingCart();
  const [showPaymentFailed, setShowPaymentFailed] = useState(false);
  const [formData, setFormData] = useState({
    products: [],
    phone: "",
    email: "",
    card_number: "",
    expire_date_m: "",
    expire_date_y: "",
    payment_mode: "VISA",
    card_name: "",
    address_line_one: "",
    address_line_two: "",
    apt: "",
    city: "",
    state: "",
  });

  let navigate = useNavigate();
  const routeChange = (path) => {
    navigate(path);
  };

  const api = new OrderApi();

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    console.log(`field: ${name} changed, value: ${value}`);
    setFormData((prev) => {
      return { ...prev, [name]: value };
    });
  };

  const handleNumberInput = (event) => {
    const value = event.target.value;
    event.target.value = value
      .replace(/[^0-9.]/g, "")
      .replace(/(\..*)\./g, "$1");
  };

  async function handleSubmit(event) {
    //TODO validation before
    event.preventDefault();

    const orderRequest = {
      products: cartItems,
      payment_details: {
        payment_mode: formData.payment_mode,
        card_number: formData.card_number,
        card_name: formData.card_name,
        expire_date: `${formData.expire_date_m}/${formData.expire_date_y}`,
        email: formData.email,
        phone: formData.phone,
      },
    };
    let statusCode;
    const resp = await api
      .placeOrderPromise(orderRequest)
      .then((response) => {
        statusCode = response.status;
        return response.json();
      })
      .catch((error) => console.error(error));

    console.log(resp);
    console.log(statusCode);

    if (statusCode === 201) {
      if (resp.status === "PLACED") {
        onHide();
        closeCart();
        clearCart();
        navigate(`/store/order/${resp.id}`);
      } else {
        setShowPaymentFailed(true);
      }
    }
  }

  return (
    <Modal
      show={show}
      onHide={onHide}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton className="rounded-0">
        <Modal.Title id="contained-modal-title-vcenter">Checkout</Modal.Title>
      </Modal.Header>
      <Modal.Body className="rounded-0">
        <Form>
          <div className="mb-2 shadow-sm rounded">
            <span className="fs-5 m-2">Customer info</span>
            <Form.Group className="d-flex gap-4 shadow-sm rounded ">
              <Form.Group className="mb-3 w-50 m-2" controlId="email">
                <Form.Label>Email address</Form.Label>
                <Form.Control
                  type="email"
                  placeholder="name@example.com"
                  autoFocus
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                />
              </Form.Group>
              <Form.Group className="mb-3  w-50 m-2" controlId="phone">
                <Form.Label>Phone</Form.Label>
                <Form.Control
                  onInput={handleNumberInput}
                  maxLength={10}
                  type="tel"
                  inputMode="decimal"
                  placeholder="(000) 000-0000"
                  value={formData.phone}
                  autoComplete="off"
                  name="phone"
                  onChange={handleChange}
                />
              </Form.Group>
            </Form.Group>
          </div>

          <div className="mb-2 mt-3 shadow-sm rounded">
            <span className="fs-5 m-2">Credit card info</span>
            <Form.Group className="d-flex gap-4">
              <Form.Group className="mb-3 w-75 m-2" controlId="card_number">
                <Form.Label>Card number</Form.Label>
                <Form.Control
                  type="text"
                  onInput={handleNumberInput}
                  maxLength={16}
                  minLength={16}
                  placeholder="0000 0000 0000 0000"
                  autoComplete="off"
                  name="card_number"
                  onChange={handleChange}
                />
              </Form.Group>
              <Form.Group className="mb-3 w-25 m-2" controlId="cvv">
                <Form.Label>CVC \ CVV</Form.Label>
                <Form.Control
                  onInput={handleNumberInput}
                  maxLength={3}
                  minLength={3}
                  type="password"
                  placeholder="000"
                  autoComplete="off"
                  name="cvv"
                  onChange={handleChange}
                />
              </Form.Group>
            </Form.Group>
            <Form.Group className="d-flex gap-4 shadow-sm rounded">
              <Form.Group className="mb-3 w-75 m-2" controlId="card_name">
                <Form.Label>Name on card</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Jack Jones"
                  autoComplete="off"
                  name="card_name"
                  onChange={handleChange}
                />
              </Form.Group>
              <Form.Group className="mb-3 w-25 mt-2" controlId="expire_date_m">
                <Form.Label>Expire date</Form.Label>
                <Form.Control
                  onInput={handleNumberInput}
                  maxLength={2}
                  type="text"
                  placeholder="MM"
                  name="expire_date_m"
                  onChange={handleChange}
                />
              </Form.Group>
              <div className="fs-4 d-flex align-items-md-center">/</div>
              <Form.Group
                className="mt-3 mb-3 w-25 m-1"
                controlId="expire_date_y"
              >
                <Form.Label></Form.Label>
                <Form.Control
                  onInput={handleNumberInput}
                  maxLength={2}
                  type="text"
                  placeholder="YY"
                  name="expire_date_y"
                  onChange={handleChange}
                />
              </Form.Group>
            </Form.Group>
          </div>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <div>
          {`Total amount: `}
          <span className="fs-4">{formatCurrency(totalAmount)}</span>
        </div>
        <Button type="submit" className="btn-success" onClick={handleSubmit}>
          Place Order
        </Button>
        <Button className="btn-danger" onClick={onHide}>
          Close
        </Button>
      </Modal.Footer>
      <PaymentFailed
        show={showPaymentFailed}
        onHide={() => setShowPaymentFailed(false)}
      />
    </Modal>
  );
}
