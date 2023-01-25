import React, { useState } from "react";
import { Transition } from "react-transition-group";
import Logo from "../Logo";
import HeaderNavbar from "../Navbar/HeaderNavbar";
import StoreNavbarMini from "../Shop/StoreNavbar/StoreNavbarMini";
import "./Header.css";

const Header = ({ isTopOfPage }) => {
  const [active, setActive] = useState("home");
  const [over, setOver] = useState("home");
  return (
    <header
      id="header"
      className={`header bg-dark ${isTopOfPage ? "header-top" : "header-mini"}`}
    >
      <Logo styles={{ zIndex: "1000" }} />

      <HeaderNavbar
        isTopOfPage={isTopOfPage}
        setActive={setActive}
        active={active}
        setOver={setOver}
      />
      <Transition
        timeout={300}
        // in={active === "Store" || over === "Store"}
        in={active === "store"}
        unmountOnExit={false}
        mountOnEnter={false}
      >
        {(state) => (
          <StoreNavbarMini
            state={state}
            isTopOfPage={isTopOfPage}
            pathPrefix={active}
          />
        )}
      </Transition>
    </header>
  );
};

export default Header;
