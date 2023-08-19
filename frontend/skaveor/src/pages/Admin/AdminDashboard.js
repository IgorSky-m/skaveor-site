import React, { useEffect, useState } from "react";
import { Button, Container, Stack } from "react-bootstrap";
import { NavLink, useNavigate } from "react-router-dom";
import { useLogin } from "../../context/LoginContext";
import wrap from "../../utilities/ApiCallWrapper";
const AdminDashboard = () => {
  const { getRoles, logIn } = useLogin();
  const navigate = useNavigate();
  const [showPanel, setShowPanel] = useState(false);
  const forbidden = () => {
    navigate("/forbidden");
  };

  useEffect(() => {
    wrap(getRoles)
      .success((roles) => {
        if (!roles.includes("ADMIN")) {
          forbidden();
        } else {
          setShowPanel(true);
        }
      })
      .unauthorized(logIn)
      .forbidden(forbidden)
      .excute();

    return () => {};
  }, []);

  return (
    <Container hidden={!showPanel}>
      <div className="p-4">
        <h1 className="fs-3 text-white text-center">Admin panel</h1>
      </div>

      <Stack className="">
        <Button
          to="users"
          as={NavLink}
          className="rounded-0 "
          variant="outline-secondary"
        >
          Users
        </Button>
        <Button
          to="categories"
          as={NavLink}
          className="rounded-0 "
          variant="outline-secondary"
        >
          Categories
        </Button>
      </Stack>
    </Container>
  );
};

export default AdminDashboard;
