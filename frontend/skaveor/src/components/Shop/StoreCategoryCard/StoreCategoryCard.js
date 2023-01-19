import React from "react";
import { Card } from "react-bootstrap";
import { Link } from "react-router-dom";
import "./StoreCategoryCard.css";

const StoreCategoryCard = ({ item }) => {
  return (
    <Card as={Link} to={item.id} className="h-100 bg-dark text-light">
      <Card.Img
        className="rounded-0"
        variant="top"
        src={item.icon}
        height="200px"
        style={{ objectFit: "cover" }}
      />

      <Card.Body className="rounded-0 d-flex flex-column card-img-overlay border-0 card-block just-cont-end op-block">
        <Card.Title className="text-uppercase mb-4">
          <span className="fs-2">{item.name}</span>
        </Card.Title>
        <Card.Text>{item.description}</Card.Text>
      </Card.Body>
    </Card>
  );
};

export default StoreCategoryCard;
