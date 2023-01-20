import React from "react";
import Logo from "../Logo";
import Navbar from "../Navbar/Navbar";
import "./Header.css";

const Header = ({ isTopOfPage }) => {
  return (
    <header
      id="header"
      className={isTopOfPage ? "header header-top" : "header header-mini"}
    >
      <Logo />

      <Navbar isTopOfPage={isTopOfPage} />
    </header>
  );
};

export default Header;
