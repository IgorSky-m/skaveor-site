import React, { useEffect } from "react";
import { Container } from "react-bootstrap";
import { useLogin } from "../context/LoginContext";

const Game = () => {
  const { loginCheck } = useLogin();

  useEffect(() => {
    loginCheck();
  }, []);

  return (
    <Container className="mt-3 text-center text-white text-shadow-cls">
      <h1>
        <span style={{ fontFamily: "Glitch" }}>Cooming soon</span>
      </h1>
    </Container>
  );
};

export default Game;
