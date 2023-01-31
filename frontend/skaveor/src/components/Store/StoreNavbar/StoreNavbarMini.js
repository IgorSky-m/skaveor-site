import React, { useEffect } from "react";
import {
  createSearchParams,
  useSearchParams,
  NavLink,
  useNavigate,
} from "react-router-dom";
import "./StoreNavbar.css";
import { useState } from "react";
import { Nav, Navbar, Button, Form } from "react-bootstrap";

const StoreNavbarMini = ({ state, pathPrefix }) => {
  const [search, setSearch] = useState("");
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();
  const [currentState, setCurrentState] = useState("");
  useEffect(() => {
    setCurrentState(state);
    return () => {};
  }, []);

  return (
    <Navbar
      className={`d-flex justify-content-start shadow-lg mb-3 bg-dark text-uppercase p-2 gap-3 store-navbar ${state}`}
      fixed="top"
      variant="dark"
      style={{ height: "40px", fontWeight: "400" }}
    >
      <Nav
        className="p-4 gap-3"
        style={{ marginLeft: "250px", marginRight: "auto" }}
      >
        <Nav.Link as={NavLink} to="/store/categories">
          Categories
        </Nav.Link>
        <Nav.Link as={NavLink} to="/store/deals">
          Deals
        </Nav.Link>
      </Nav>
      <Form
        className="d-flex m-3"
        onSubmit={(event) => {
          event.preventDefault();
          navigate({
            pathname: "/store/search",
            search: `?${createSearchParams({ s: search })}`,
          });
        }}
      >
        <Form.Control
          type="text"
          placeholder="SEARCH"
          className="me-1 rounded-0"
          aria-label="Search"
          name="search"
          onChange={(event) => {
            setSearch(event.target.value);
            if (event.target.value.length >= 2) {
              setSearchParams((nextInit, prev) => {
                return {
                  ...prev,
                  s: event.target.value,
                };
              });
            }
          }}
          value={search}
        />
        <Button
          variant="outline-secondary rounded-0"
          onClick={() =>
            navigate({
              pathname: "/store/search",
              search: `?${createSearchParams({ s: search })}`,
            })
          }
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            className="bi bi-search"
            viewBox="0 0 16 16"
          >
            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
          </svg>
        </Button>
      </Form>
    </Navbar>
  );
};

export default StoreNavbarMini;
