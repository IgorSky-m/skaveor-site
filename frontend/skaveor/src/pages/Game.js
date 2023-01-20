import React, { useEffect } from "react";
import { useLogin } from "../context/LoginContext";

const Game = () => {
  const { loginCheck } = useLogin();

  useEffect(() => {
    loginCheck();
  }, []);

  return <div>Game</div>;
};

export default Game;
