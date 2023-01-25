import React from "react";
import { Link } from "react-router-dom";

const Logo = ({ classes, styles = {} }) => {
  return (
    <Link to="/" className="text-decoration-none">
      <div
        style={styles}
        className={`header-logo-wrapper p-3 logo-setup ${classes}`}
      >
        {/* <img
            className={isTopOfPage ? "header-logo-img" : "header-logo-img-mini"}
            src="/img/logo/skaveor-high-resolution-logo-color-on-transparent-background.png"
            alt="logo"
          /> */}
        SKAVEOR
      </div>
    </Link>
  );
};

export default Logo;
