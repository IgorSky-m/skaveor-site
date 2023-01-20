import React, { useState } from "react";
import { Button, Form, Modal, Spinner } from "react-bootstrap";
import formatCurrency from "../../utilities/formatCurrency";
import OrderApi from "../../data/OrderRestApi";
import { useNavigate } from "react-router-dom";
import { useShoppingCart } from "../../context/ShoppingCartContext";
import PaymentFailed from "./PaymentFailed";
import { useLogin } from "../../context/LoginContext";

export default function PaymentModal({ show, onHide, totalAmount }) {
  const { cartItems, closeCart, clearCart } = useShoppingCart();
  const [showPaymentFailed, setShowPaymentFailed] = useState(false);
  const { getAuthHeader } = useLogin();
  const [loading, setLoading] = useState(false);
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

  const [classes, setClasses] = useState({
    phone: "",
    email: "",
    card_number: "",
    expire_date_m: "",
    expire_date_y: "",
    payment_mode: "",
    card_name: "",
    address_line_one: "",
    address_line_two: "",
    apt: "",
    city: "",
    state: "",
  });

  const [validationErrors, setValidationErrors] = useState({
    phone: "",
    email: "",
    card_number: "",
    expire_date_m: "",
    expire_date_y: "",
    payment_mode: "",
    card_name: "",
    address_line_one: "",
    address_line_two: "",
    apt: "",
    city: "",
    state: "",
  });

  const navigate = useNavigate();

  const api = new OrderApi();

  const isEmail = (email) =>
    /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(email);

  const validateEmail = () => {
    let msg;
    let emailClasses;
    if (formData.email === "") {
      msg = "email empty";
      emailClasses = "invalid-input";
    } else if (!isEmail(formData.email)) {
      msg = "invalid email";
      emailClasses = "invalid-input";
    } else {
      msg = "";
      emailClasses = "";
    }
    setValidationErrors((prev) => {
      return { ...prev, email: msg };
    });
    setClasses((prev) => {
      return { ...prev, email: emailClasses };
    });
  };

  const isPhoneNumber = (phone) =>
    /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/.test(phone);

  const validatePhone = () => {
    let msg;
    let cls;
    if (formData.phone === "") {
      msg = "phone empty";
      cls = "invalid-input";
    } else if (!isPhoneNumber(formData.phone)) {
      msg = "invalid phone";
      cls = "invalid-input";
    } else {
      msg = "";
      cls = "";
    }
    setValidationErrors((prev) => {
      return { ...prev, phone: msg };
    });
    setClasses((prev) => {
      return { ...prev, phone: cls };
    });
  };

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setFormData((prev) => {
      return { ...prev, [name]: value };
    });
  };

  async function handleSubmit(event) {
    //TODO validation before
    event.preventDefault();
    setLoading(true);

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
      .placeOrder(orderRequest, getAuthHeader())
      .then((response) => {
        statusCode = response.status;
        return response.json();
      })
      .catch((error) => console.error(error));

    if (statusCode === 200 || statusCode === 201) {
      if (resp.status === "PLACED") {
        onHide();
        closeCart();
        clearCart();
        navigate(`/store/order/${resp.id}`);
      } else {
        setShowPaymentFailed(true);
      }
    }
    setLoading(false);
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
                  onChange={(e) => {
                    handleChange(e);
                    validateEmail();
                  }}
                  className={classes.email}
                />
              </Form.Group>
              <Form.Group className="mb-3  w-50 m-2" controlId="phone">
                <Form.Label>Phone</Form.Label>
                <Form.Control
                  maxLength={10}
                  type="tel"
                  inputMode="decimal"
                  placeholder="000-000-0000"
                  value={formData.phone}
                  autoComplete="off"
                  name="phone"
                  onChange={(e) => {
                    handleChange(e);
                    validatePhone();
                  }}
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
        <Button
          disabled={loading}
          type="submit"
          className="btn-success"
          onClick={handleSubmit}
        >
          {loading ? (
            <Spinner
              as="span"
              animation="border"
              size="sm"
              role="status"
              aria-hidden="true"
            />
          ) : (
            <>Place order</>
          )}
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
