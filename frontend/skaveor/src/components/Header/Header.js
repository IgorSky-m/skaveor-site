import React from "react";
import { Link } from "react-router-dom";
import Navbar from "../Navbar/Navbar";
import "./Header.css";

const Header = ({ isTopOfPage }) => {
  return (
    <header
      id="header"
      className={isTopOfPage ? "header header-top" : "header header-mini"}
    >
      <div className="header-logo-wrapper">
        <Link to="/">
          <img
            className={isTopOfPage ? "header-logo-img" : "header-logo-img-mini"}
            src="/img/logo/skaveor-high-resolution-logo-color-on-transparent-background.png"
            alt="logo"
          />
        </Link>
      </div>
      <Navbar isTopOfPage={isTopOfPage} />
    </header>
  );
};

export default Header;
