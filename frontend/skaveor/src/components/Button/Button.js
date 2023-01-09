import React from "react";
import { Link } from "react-router-dom";

import "./Button.css";

const DEFAULT_BUTTON_TEXT = "Click";
const DEFAULT_PATH = "/";
const DEFAULT_ON_CLICK = () => {};

const Button = ({ path, buttonText, onClick }) => {
  return (
    <Link
      onClick={onClick ? onClick : DEFAULT_ON_CLICK}
      className="content-btn-link"
      to={path ? path : DEFAULT_PATH}
    >
      <div className="content-btn">
        <p className="content-btn-text">
          {buttonText ? buttonText : DEFAULT_BUTTON_TEXT}
        </p>
      </div>
    </Link>
  );
};

export default Button;
