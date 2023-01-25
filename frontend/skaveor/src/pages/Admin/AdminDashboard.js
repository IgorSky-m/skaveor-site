import React from "react";
import { Button, Container, Stack } from "react-bootstrap";
import { NavLink } from "react-router-dom";

const AdminDashboard = () => {
  return (
    <Container>
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
