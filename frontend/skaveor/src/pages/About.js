import React from "react";
import { Container, Form } from "react-bootstrap";

const About = () => {
  return (
    <Container className="mt-3 text-center text-white text-shadow-cls">
      <h1>
        <span style={{ fontFamily: "Glitch" }}>Igar Skachko</span>
      </h1>
      <h3> Fullstack developer</h3>
      <a className="text-white fs-4 " href="https://github.com/">
        LinkedIn
      </a>
    </Container>
  );
};

export default About;
