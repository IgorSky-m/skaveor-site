import React from "react";
import { Link } from "react-router-dom";
import "./StoreNavbar.css";
import { useState } from "react";

const StoreNavbar = () => {
  return (
    <nav className="store-navbar">
      <div className="store-navbar-block">
        <Link to="/store">Main</Link>
        <Link to="categories">Categories</Link>
        <Link to="deals">Deals</Link>
      </div>

      <div className="store-search-block">
        <input type="text" placeholder="search..." />
        <button>search</button>
      </div>
    </nav>
  );
};

export default StoreNavbar;
