import React from "react";
import { Button, Card, Container } from "react-bootstrap";
import { Link } from "react-router-dom";
import { useShoppingCart } from "../../../context/ShoppingCartContext";
import formatCurrency from "../../../utilities/formatCurrency";
// import "./ItemCard.css";
const ItemCard = ({ item }) => {
  const {
    getItemQuantity,
    increaseItemQuantity,
    decreaseItemQuantity,
    removeFromCart,
  } = useShoppingCart();
  const quantity = getItemQuantity(item.id);
  return (
    <Container className="text-shadow-cls ">
      <Card className="h-100 rounded-0 bg-dark text-light rounded-0">
        <Card.Img
          className="rounded-0"
          variant="top"
          src={item.title_picture}
          height="200px"
          style={{ objectFit: "cover" }}
        />

        <Card.Body
          as={Link}
          to={`/store/items/${item.id}`}
          className="d-flex flex-column card-img-overlay border-0 card-block rounded-0 op-block text-light text-decoration-none"
        >
          <Card.Title className="d-flex justify-content-between align-items-baseline text-uppercase mb-4">
            <span className="fs-3">{item.title}</span>
            <span className="ms-2 text-muted">
              {formatCurrency(item.price)}
            </span>
          </Card.Title>
          <div
            className="d-flex gap-3"
            style={{ position: "absolute", bottom: "10px" }}
          >
            {item.deals &&
              item.deals.map((e) => {
                if (e.type !== null) {
                  return <div className="bg-danger p-1">{e.type}</div>;
                }
              })}
          </div>
        </Card.Body>
      </Card>
      <div className="mt-auto rounded-0 bg-dark p-1 border border-secondary">
        {quantity === 0 ? (
          <Button
            onClick={() => increaseItemQuantity(item.id)}
            className="w-100 rounded-0 bg-dark border-0 mt-1"
          >
            + Add To Cart
          </Button>
        ) : (
          <div
            className="d-flex justify-content-center flex-column bg-dark"
            style={{ gap: ".5rem" }}
          >
            <div
              className="d-flex align-items-center justify-content-center border-secondary mt-1 mb-1"
              style={{ gap: ".5rem" }}
            >
              <Button
                onClick={() => decreaseItemQuantity(item.id)}
                className="rounded-0 border-secondary"
                size="sm"
                style={{
                  width: "2rem",
                  height: "2rem",
                  position: "relative",
                }}
                variant="outline-light"
              >
                -
              </Button>
              <div>
                <span className="fs 5">{quantity}</span> in cart
              </div>
              <Button
                onClick={() => increaseItemQuantity(item.id)}
                className="rounded-0 border-secondary"
                size="sm"
                style={{
                  width: "2rem",
                  height: "2rem",
                  position: "relative",
                }}
                variant="outline-light"
              >
                +
              </Button>
              {/* <Button
                onClick={() => removeFromCart(item.id)}
                variant="danger"
                size="sm rounded-0"
                style={{
                  width: "2rem",
                  height: "2rem",
                  position: "relative",
                }}
              >
                &times;
              </Button> */}
            </div>
          </div>
        )}
      </div>
    </Container>
  );
};

export default ItemCard;
