import React from "react";
import { Link } from "react-router-dom";
import "./ItemCard.css";
const ItemCard = ({ item }) => {
  return (
    <Link to={`/store/items/${item.id}`}>
      <div className="store-item-card">
        <h5>{item.id}</h5>
        <h5>{item.title}</h5>
      </div>
    </Link>
  );
};

export default ItemCard;
