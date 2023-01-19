import React, { useState } from "react";
import { Button, Col, Container, Form, Modal, Row } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { useLogin } from "../../context/LoginContext";
import AuthApi from "../../data/AuthApi";
export default function Login({ show, onHide, goBack }) {
  const [loginForm, setLoginForm] = useState({
    email: "",
    password: "",
  });
  const [responseError, setResponseError] = useState([]);
  const [classes, setClasses] = useState({
    email: "",
    password: "",
  });
  const [validationErrors, setValidationErrors] = useState({
    email: "",
    password: "",
  });
  const { openRegister, isLogged, logIn, close } = useLogin();
  const navigate = useNavigate();
  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setLoginForm((prev) => {
      return { ...prev, [name]: value };
    });
  };

  const isEmail = (email) =>
    /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(email);

  const validateEmail = () => {
    let msg;
    let emailClasses;
    if (loginForm.email === "") {
      msg = "email empty";
      emailClasses = "invalid-input";
    } else if (!isEmail(loginForm.email)) {
      msg = "invalid email";
      emailClasses = "invalid-input";
    } else {
      msg = "";
      emailClasses = "";
    }
    setValidationErrors((prev) => {
      return { ...prev, email: msg };
    });

    setClasses((prev) => {
      return { ...prev, email: emailClasses };
    });
  };

  const validatePassword = () => {
    let msg;
    let passClasses;
    if (loginForm.password === "") {
      msg = "password empty";
      passClasses = "invalid-input";
    } else {
      msg = "";
      passClasses = "";
    }
    setValidationErrors((prev) => {
      return { ...prev, password: msg };
    });

    setClasses((prev) => {
      return { ...prev, password: passClasses };
    });
  };

  async function handleSubmit(event) {
    event.preventDefault();

    let responseCode = 200;
    const result = await new AuthApi()
      .login(loginForm)
      .then((response) => {
        responseCode = response.status;
        return response.json();
      })
      .catch((err) => {
        console.log(err);
        //todo toast with exception
        return "";
      });

    if (responseCode === 200) {
      const jsonToken = result.token;
      logIn(jsonToken);
      close();
    } else if (responseCode === 400) {
      console.log(result);
      if (Array.isArray(result)) {
        setValidationErrors((prev) => {
          let obj = { ...prev };
          result.map((e) => (obj = { ...obj, [e.field]: e.msg }));
          return obj;
        });

        setClasses((prev) => {
          let obj = { ...prev };
          result.map((e) => (obj = { ...obj, [e.field]: "invalid-input" }));
          return obj;
        });
      }
    }

    //TODO validation before
  }

  const handleHide = async () => {
    //todo check login
    if ((await isLogged()) === false) {
      if (goBack) {
        navigate(-1);
      }
    }
    onHide();
  };

  return (
    <Modal
      show={show}
      onHide={handleHide}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
      className="modal-rounded-0 bg-rgba"
    >
      <div className=" ">
        <Modal.Header className=" text-white rounded-0 shadow-lg border-0 bg-dark box-block">
          <Container>
            <Row className=" d-flex flex-column  align-items-end justify-content-between">
              <Col className=" text-center fs-2 mb-5 text-uppercase">
                <img
                  src="/img/logo/skaveor-high-resolution-logo-color-on-transparent-background.png"
                  alt="logo"
                  style={{ width: "200px" }}
                />
              </Col>
              <Col className=" text-center mt-3 fs-5">
                Sign in with your account
              </Col>
            </Row>
          </Container>
          <Modal.Title
            className="text-white text-shadow-cls"
            id="contained-modal-title-vcenter"
          ></Modal.Title>
        </Modal.Header>

        <Modal.Body className=" bg-dark">
          <Form autoComplete="off" className=" text-center">
            <Form.Group
              className={`w-50 mt-3 m-2 mb-4 text-center m-auto w-50 `}
              controlId="email"
            >
              <Form.Control
                onBlur={validateEmail}
                type="email"
                placeholder="Email address"
                autoFocus
                autoComplete="off"
                name="email"
                value={loginForm.email}
                onChange={(event) => {
                  handleChange(event);
                  validateEmail();
                }}
                className={`bg-dark rounded-0 text-white p-3 input-drk ${classes.email}`}
                style={{ position: "relative" }}
              />
              {validationErrors.email && (
                <div
                  className="text-danger fs-10 m-0 p-0"
                  style={{
                    position: "absolute",
                    top: "88px",
                    right: "360px",
                    fontSize: "14px",
                  }}
                >
                  {validationErrors.email}
                </div>
              )}
            </Form.Group>
            <Form.Group
              className="mb-5 w-50 mt-3 m-2 mb-4 text-center m-auto w-50 "
              controlId="password"
              style={{ position: "relative" }}
            >
              <Form.Control
                type="password"
                onBlur={validatePassword}
                placeholder="Password"
                name="password"
                value={loginForm.password}
                onChange={(event) => {
                  handleChange(event);
                  validatePassword();
                }}
                className={`bg-dark rounded-0 text-white p-3 input-drk ${classes.password}`}
              />
              {validationErrors.password && (
                <div
                  className="text-danger fs-10 m-0 p-0"
                  style={{
                    position: "absolute",
                    top: "55px",
                    right: "100px",
                    fontSize: "14px",
                    width: "180px",
                  }}
                >
                  {validationErrors.password}
                </div>
              )}
            </Form.Group>
            <Button
              type="submit"
              className="btn-success rounded-0 mb-3 p-3 w-50 text-uppercase"
              onClick={handleSubmit}
              disabled={
                validationErrors.email !== "" ||
                validationErrors.password !== ""
              }
            >
              Log in now
            </Button>
          </Form>
        </Modal.Body>
        <Modal.Footer className="bg-dark rounded-0 border-0">
          <Container className="text-center">
            <h5 className="text-white fs-5 mb-4">
              Don't have an account yet?{" "}
              <Link
                className="text-white text-shadow"
                onClick={() => openRegister(goBack)}
              >
                Sign Up
              </Link>{" "}
            </h5>
          </Container>
        </Modal.Footer>
      </div>
    </Modal>
  );
}
