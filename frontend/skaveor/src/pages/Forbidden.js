import React from "react";
import { Button, Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const Forbidden = () => {
  const navigate = useNavigate();
  return (
    <>
      <Container>
        <div className="text-center">OOPS... Forbidden</div>
        <Button onClick={() => navigate("/")}>Home</Button>
      </Container>
    </>
  );
};

export default Forbidden;
