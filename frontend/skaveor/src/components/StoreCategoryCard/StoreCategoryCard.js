import React from "react";
import { Link } from "react-router-dom";
import "./StoreCategoryCard.css";

const StoreCategoryCard = ({ item }) => {
  return (
    <Link to={item.id}>
      <div className="store-category-card">
        <div className="store-category-card-icon-wrapper">
          <img
            className="store-category-card-icon-img"
            src={item.icon}
            alt={item.name}
          />
        </div>
        <div className="store-category-card-text-wrapper">
          <h3 className="store-category-card-text-name">{item.name}</h3>
        </div>
      </div>
    </Link>
  );
};

export default StoreCategoryCard;
