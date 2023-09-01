import React, { useEffect, useState } from "react";
import { Button, Container, Nav, Navbar } from "react-bootstrap";
import { NavLink, useSearchParams } from "react-router-dom";
import { Transition } from "react-transition-group";
import { useAccount } from "../../context/AccountContext";
import { useLogin } from "../../context/LoginContext";
import { useShoppingCart } from "../../context/ShoppingCartContext";
import "./Navbar.css";

const HeaderNavbar = ({
  isTopOfPage,
  setActive,
  setOver,
  active,
  transitionTimeout = 500,
}) => {
  const { logged, openLogin, getPayload } = useLogin();
  const { cartQuantity, openCart } = useShoppingCart();
  const { openAccount } = useAccount();
  const [name, setName] = useState("");
  useEffect(() => {
    async function setPayload() {
      const currentPayload = await getPayload().then((resp) => resp.json());

      if (currentPayload) {
        setName(currentPayload.name);
      } else {
        setName("");
      }
    }

    setPayload();

    return () => {};
  }, []);

  const cartTransitionStyles = {
    entering: { opacity: 0.5 },
    entered: { opacity: 1 },
    exiting: { opacity: 0.5 },
    exited: { opacity: 0 },
  };

  const cartDefaultStyles = {
    width: "2.7rem",
    height: "2.7rem",
    position: "relative",
  };

  const getCartStyles = (state, cartQuantity) => {
    if (cartQuantity > 0) {
      return {
        ...cartDefaultStyles,
        ...cartTransitionStyles.entered,
      };
    } else {
      return {
        ...cartDefaultStyles,
        ...cartTransitionStyles[state],
      };
    }
  };

  const isCartButtonDisabled = (state, cartQuantity) => {
    return cartQuantity === 0 && (state === "exiting" || state === "exited");
  };

  return (
    <Navbar variant="dark" className="w-100 p-0 m-0">
      <Nav
        className="d-flex m-0 p-0 w-100"
        // className={`navbar-main text-uppercase border ${
        //   isTopOfPage ? "navbar navbar-top" : "navbar navbar-mini"
        // }`}
      >
        <Container className=" d-flex p-0 gap-3" style={{ marginLeft: "50px" }}>
          <Nav.Link
            onClick={() => setActive("home")}
            onMouseOver={() => setOver("home")}
            className="text-uppercase text-shadow-cls navbar-link"
            as={NavLink}
            to="/"
          >
            Home
          </Nav.Link>
          <Nav.Link
            onClick={() => setActive("store")}
            onMouseOver={() => setOver("store")}
            className="text-uppercase text-shadow-cls navbar-link"
            as={NavLink}
            to="/store"
          >
            Store
          </Nav.Link>
          <Nav.Link
            onClick={() => setActive("game")}
            onMouseOver={() => setOver("game")}
            className="text-uppercase text-shadow-cls navbar-link"
            as={NavLink}
            to="/game"
          >
            Game
          </Nav.Link>
          <Nav.Link
            onClick={() => setActive("news")}
            onMouseOver={() => setOver("news")}
            className="text-uppercase text-shadow-cls navbar-link"
            as={NavLink}
            to="/news"
          >
            News
          </Nav.Link>
          <Nav.Link
            onClick={() => setActive("about")}
            onMouseOver={() => setOver("about")}
            className="text-uppercase text-shadow-cls navbar-link"
            as={NavLink}
            to="/about"
          >
            About
          </Nav.Link>

          {/* //------------------------ */}
          <Container className="d-flex justify-content-end m-0 p-0 gap-3">
            <Transition in={active === "store" && logged} timeout={transitionTimeout}>
              {(state) => (
                <Button
                  onClick={openCart}
                  style={getCartStyles(state, cartQuantity)}
                  disabled={isCartButtonDisabled(state, cartQuantity)}
                  variant={
                    cartQuantity === 0 ? "outline-secondary" : "outline-light"
                  }
                  className={`rounded-0 border-0 card-button ${
                    cartQuantity > 0 ? "entered" : state
                  }`}
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="50 0 550 550"
                    fill="currentColor"
                  >
                    <path d="M96 0C107.5 0 117.4 8.19 119.6 19.51L121.1 32H541.8C562.1 32 578.3 52.25 572.6 72.66L518.6 264.7C514.7 278.5 502.1 288 487.8 288H170.7L179.9 336H488C501.3 336 512 346.7 512 360C512 373.3 501.3 384 488 384H159.1C148.5 384 138.6 375.8 136.4 364.5L76.14 48H24C10.75 48 0 37.25 0 24C0 10.75 10.75 0 24 0H96zM128 464C128 437.5 149.5 416 176 416C202.5 416 224 437.5 224 464C224 490.5 202.5 512 176 512C149.5 512 128 490.5 128 464zM512 464C512 490.5 490.5 512 464 512C437.5 512 416 490.5 416 464C416 437.5 437.5 416 464 416C490.5 416 512 437.5 512 464z" />
                  </svg>

                  {cartQuantity !== 0 && (
                    <div
                      className="rounded-circle bg-danger d-flex justify-content-center align-items-center"
                      style={{
                        color: "white",
                        width: "1.5rem",
                        height: "1.5rem",
                        position: "absolute",
                        bottom: 0,
                        right: 0,
                        transform: "translate(25%, 25%)",
                      }}
                    >
                      {cartQuantity}
                    </div>
                  )}
                </Button>
              )}
            </Transition>

            {!logged ? (
              <>
                <Button
                  onClick={() => openLogin(false)}
                  style={{
                    width: "4rem",
                    height: "2.7rem",
                    position: "relative",
                  }}
                  variant="outline-secondary"
                  // variant="outline-light"
                  className="rounded-0 d-flex align-items-center border-0"
                > Login
                  {/* <svg
                    fill="currentColor"
                    viewBox="0 -4 22 30"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <g id="SVGRepo_bgCarrier" strokeWidth="0"></g>
                    <g
                      id="SVGRepo_tracerCarrier"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                    ></g>
                    <g id="SVGRepo_iconCarrier">
                      <path d="M20,21V3H13a1,1,0,0,1,0-2h8a1,1,0,0,1,1,1V22a1,1,0,0,1-1,1H13a1,1,0,0,1,0-2ZM2,12a1,1,0,0,0,1,1H14.586l-2.293,2.293a1,1,0,1,0,1.414,1.414l4-4a1,1,0,0,0,0-1.414l-4-4a1,1,0,1,0-1.414,1.414L14.586,11H3A1,1,0,0,0,2,12Z"></path>
                    </g>
                  </svg> */}
                </Button>
              </>
            ) : (
              <>
                <Button
                  onClick={openAccount}
                  style={{
                    width: "2.7rem",
                    height: "2.7rem",
                    position: "relative",
                  }}
                  variant="outline-secondary"
                  className="rounded-0 d-flex align-items-center border-0"
                >
                  <svg
                    viewBox="-1 -2 22 25"
                    id="meteor-icon-kit__solid-user"
                    fill="currentColor"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <g id="SVGRepo_bgCarrier" strokeWidth="0"></g>
                    <g
                      id="SVGRepo_tracerCarrier"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                    ></g>
                    <g id="SVGRepo_iconCarrier">
                      <path
                        fillRule="evenodd"
                        clipRule="evenodd"
                        d="M10 0C13.3137 0 16 2.68629 16 6C16 9.3137 13.3137 12 10 12C6.68629 12 4 9.3137 4 6C4 2.68629 6.68629 0 10 0zM1 22.099C0.44772 22.099 0 21.6513 0 21.099V19C0 16.2386 2.23858 14 5 14H15.0007C17.7621 14 20.0007 16.2386 20.0007 19V21.099C20.0007 21.6513 19.553 22.099 19.0007 22.099C18.4484 22.099 1.55228 22.099 1 22.099z"
                        fill="currentColor"
                      ></path>
                    </g>
                  </svg>
                </Button>
              </>
            )}
          </Container>
        </Container>
      </Nav>
    </Navbar>
  );
};

//  can add classname
function getLinkClasses({ isActive }) {
  return isActive ? "text-white" : "link";
}

function getPulsedLinkClasses(props) {
  const classes = getLinkClasses(props);

  return props.isActive ? classes : `pulse ${classes}`;
}

export default HeaderNavbar;
