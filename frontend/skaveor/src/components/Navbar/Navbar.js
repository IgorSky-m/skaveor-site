import React from "react";
import { NavLink } from "react-router-dom";
import "./Navbar.css";
/*
<button
        onMouseEnter={() => setIsShown(true)}
        onMouseLeave={() => setIsShown(false)}>
        Hover over me!
      </button>
      {isShown && (
        <div>
          I'll appear when you hover over the button.
        </div>

*/
const Navbar = ({ isTopOfPage }) => {
  return (
    <nav className={isTopOfPage ? "navbar navbar-top" : "navbar navbar-mini"}>
      <div className="navbar-section">
        <NavLink className={getLinkClasses} to="/">
          Home
        </NavLink>
        <NavLink className={getPulsedLinkClasses} to="/game">
          Game
        </NavLink>
        <NavLink className={getLinkClasses} to="/news">
          News
        </NavLink>
        <NavLink className={getLinkClasses} to="/about">
          About
        </NavLink>
        <NavLink className={getLinkClasses} to="/store">
          Store
        </NavLink>
      </div>
      <div className="navbar-section-login">
        <NavLink className={getLinkClasses} to="/login">
          Log in
        </NavLink>
        <NavLink className={getLinkClasses} to="/signup">
          Sign up
        </NavLink>
      </div>
    </nav>
  );
};

//  can add classname
function getLinkClasses({ isActive }) {
  return isActive ? "link active" : "link";
}

function getPulsedLinkClasses(props) {
  const classes = getLinkClasses(props);

  return props.isActive ? classes : `pulse ${classes}`;
}

export default Navbar;
