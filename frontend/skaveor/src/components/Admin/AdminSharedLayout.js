import React from "react";
import { Container } from "react-bootstrap";
import { Outlet } from "react-router-dom";

const AdminSharedLayout = () => {
  return (
    <Container className="p-0 m-0 bg-dark w-100 h-100 text-white">
      <Outlet />
    </Container>
  );
};

export default AdminSharedLayout;
