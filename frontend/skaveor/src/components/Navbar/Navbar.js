import React, { useEffect, useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { useLogin } from "../../context/LoginContext";
import "./Navbar.css";

const Navbar = ({ isTopOfPage }) => {
  const { logged, isLogged, openLogin, openRegister, logOut } = useLogin();
  const navigate = useNavigate();

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
        {!logged ? (
          <>
            <NavLink
              end
              className={getLinkClasses}
              onClick={() => openLogin(false)}
            >
              Log in
            </NavLink>
            <NavLink
              className={getLinkClasses}
              onClick={() => openRegister(false)}
            >
              Sign up
            </NavLink>
          </>
        ) : (
          <>
            <NavLink
              className={getLinkClasses}
              onClick={() => logOut(() => navigate("fd"))}
            >
              Log out
            </NavLink>
          </>
        )}
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
