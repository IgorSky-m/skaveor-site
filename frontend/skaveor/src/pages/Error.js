import React from "react";
import { Link } from "react-router-dom";

const Error = () => {
  return (
    <>
      <p>Ooops... Page not found</p>
      <Link to="/">Back</Link>
    </>
  );
};

export default Error;
