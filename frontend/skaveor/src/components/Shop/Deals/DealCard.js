import React from "react";
import { Card } from "react-bootstrap";
import { Link } from "react-router-dom";

const DealCard = ({ deal }) => {
  return (
    <Card as={Link} to={deal} className="h-100 bg-dark text-light">
      <Card.Img
        className="rounded-0"
        variant="top"
        src={`/img/deals/${deal}.jpg`}
        height="600px"
        style={{ objectFit: "cover" }}
      />

      <Card.Body className="rounded-0 d-flex flex-column card-img-overlay border-0 card-block op-block">
        <Card.Title className="text-uppercase mb-4">
          <span className="fs-2 text-shadow-cls">{deal}</span>
        </Card.Title>
      </Card.Body>
    </Card>
  );
};

export default DealCard;
