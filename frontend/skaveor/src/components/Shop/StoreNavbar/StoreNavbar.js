import React from "react";
import { NavLink, useNavigate } from "react-router-dom";
import "./StoreNavbar.css";
import { useState } from "react";
import { Nav, Navbar, Button, Form } from "react-bootstrap";
import { useShoppingCart } from "../../../context/ShoppingCartContext";

const StoreNavbar = () => {
  const { openCart, cartQuantity } = useShoppingCart();
  const [search, setSearch] = useState("");
  const navigate = useNavigate();

  return (
    <Navbar
      className="shadow-lg mb-3 bg-dark"
      fixed="top"
      sticky="top"
      variant="dark"
    >
      <Nav className="me-auto p-3">
        <Nav.Link as={NavLink} to="/" end>
          Home
        </Nav.Link>
        <Nav.Link as={NavLink} to="/store" end>
          Store
        </Nav.Link>
        <Nav.Link as={NavLink} to="categories">
          Categories
        </Nav.Link>
        <Nav.Link as={NavLink} to="deals">
          Deals
        </Nav.Link>
      </Nav>
      <Form className="d-flex m-3">
        <Form.Control
          type="search"
          placeholder="Search"
          className="me-1"
          aria-label="Search"
          name="search"
          onChange={(event) => setSearch(event.target.value)}
          value={search}
        />
        <Button
          variant="outline-secondary"
          onClick={() => navigate(`/store/search?q=${search}`)}
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            className="bi bi-search"
            viewBox="0 0 16 16"
          >
            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
          </svg>
        </Button>
      </Form>

      <Button
        onClick={openCart}
        style={{ width: "3rem", height: "3rem", position: "relative" }}
        variant={cartQuantity === 0 ? "outline-secondary" : "outline-light"}
        className="rounded-circle m-3"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 576 512"
          fill="currentColor"
        >
          <path d="M96 0C107.5 0 117.4 8.19 119.6 19.51L121.1 32H541.8C562.1 32 578.3 52.25 572.6 72.66L518.6 264.7C514.7 278.5 502.1 288 487.8 288H170.7L179.9 336H488C501.3 336 512 346.7 512 360C512 373.3 501.3 384 488 384H159.1C148.5 384 138.6 375.8 136.4 364.5L76.14 48H24C10.75 48 0 37.25 0 24C0 10.75 10.75 0 24 0H96zM128 464C128 437.5 149.5 416 176 416C202.5 416 224 437.5 224 464C224 490.5 202.5 512 176 512C149.5 512 128 490.5 128 464zM512 464C512 490.5 490.5 512 464 512C437.5 512 416 490.5 416 464C416 437.5 437.5 416 464 416C490.5 416 512 437.5 512 464z" />
        </svg>

        {cartQuantity !== 0 && (
          <div
            className="rounded-circle bg-danger d-flex justify-content-center align-items-center"
            style={{
              color: "white",
              width: "1.5rem",
              height: "1.5rem",
              position: "absolute",
              bottom: 0,
              right: 0,
              transform: "translate(25%, 25%)",
            }}
          >
            {cartQuantity}
          </div>
        )}
      </Button>
    </Navbar>
  );
};

export default StoreNavbar;
