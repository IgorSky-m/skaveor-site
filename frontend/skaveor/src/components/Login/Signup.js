import React, { useState } from "react";
import {
  Button,
  Col,
  Container,
  Form,
  Modal,
  Row,
  Spinner,
} from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { useLogin } from "../../context/LoginContext";
import AuthApi from "../../data/AuthApi";
import Logo from "../Logo";
export default function Signup({ show, onHide, goBack }) {
  const [loading, setLoading] = useState(false);
  const [loginForm, setLoginForm] = useState({
    email: "",
    password: "",
    name: "",
  });
  const [responseError, setResponseError] = useState([]);
  const [classes, setClasses] = useState({
    email: "",
    password: "",
    name: "",
  });
  const [validationErrors, setValidationErrors] = useState({
    email: "",
    password: "",
    name: "",
  });
  const { openLogin, isLogged, logIn, close } = useLogin();
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

  const isValidPassword = (pass) =>
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/.test(
      pass
    );

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

  const validateName = () => {};

  const validatePassword = () => {
    let msg;
    let passClasses;
    if (loginForm.password === "") {
      msg = "password empty";
      passClasses = "invalid-input";
    } else if (!isValidPassword(loginForm.password)) {
      msg =
        "Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character";
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
    setLoading(true);
    event.preventDefault();

    let responseCode = 200;
    const result = await new AuthApi()
      .register(loginForm)
      .then((response) => {
        responseCode = response.status;
        return response.json();
      })
      .catch((err) => {
        console.log(err);
        setLoading(false);
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
    setLoading(false);
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

  const isDisabledButton = () => {
    return (
      validationErrors.email !== "" ||
      validationErrors.password !== "" ||
      validationErrors.name !== ""
    );
  };

  return (
    <Modal
      show={show}
      onHide={handleHide}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
      className="modal-rounded-0 mb-0 bg-rgba"
    >
      <div className=" ">
        <Modal.Header className=" text-white rounded-0 shadow-lg border-0 bg-dark box-block">
          <Container>
            <Row className=" d-flex flex-column  align-items-end justify-content-between">
              <Col className=" text-center fs-2 mb-5 text-uppercase">
                <Logo classes={"fs-2 mt-3 mb-0"} />
              </Col>
              <Col className=" text-center mt-0 fs-5">Create your account</Col>
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
              controlId="name"
            >
              <Form.Control
                onBlur={validateName}
                type="text"
                placeholder="Name"
                autoFocus
                autoComplete="off"
                name="name"
                value={loginForm.name}
                onChange={(event) => {
                  handleChange(event);
                  validateName();
                }}
                className={`bg-dark rounded-0 text-white p-3 input-drk ${classes.name}`}
                style={{ position: "relative" }}
              />
              {validationErrors.name && (
                <div
                  className="text-danger fs-10 m-0 p-0"
                  style={{
                    position: "absolute",
                    top: "88px",
                    right: "360px",
                    fontSize: "14px",
                  }}
                >
                  {validationErrors.name}
                </div>
              )}
            </Form.Group>

            <Form.Group
              className={`w-50 mt-3 m-2 mb-4 text-center m-auto w-50 `}
              controlId="email"
            >
              <Form.Control
                onBlur={validateEmail}
                type="email"
                placeholder="Email address"
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
                    top: "170px",
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
                    right: "-50px",
                    fontSize: "14px",
                    width: "500px",
                  }}
                >
                  {validationErrors.password}
                </div>
              )}
            </Form.Group>
            <Button
              style={{ fontFamily: "GameCube" }}
              type="submit"
              className="btn-success rounded-0 mb-3 p-3 w-50 text-uppercase"
              onClick={handleSubmit}
              disabled={isDisabledButton()}
            >
              {loading ? (
                <Spinner
                  as="span"
                  animation="border"
                  size="sm"
                  role="status"
                  aria-hidden="true"
                />
              ) : (
                <>Register</>
              )}
            </Button>
          </Form>
        </Modal.Body>
        <Modal.Footer className="bg-dark rounded-0 border-0">
          <Container className="text-center">
            <h5 className="text-white fs-5 mb-4">
              Alreary have an account?{" "}
              <Link
                className="text-white text-shadow"
                onClick={() => openLogin(goBack)}
              >
                Sign In
              </Link>{" "}
            </h5>
          </Container>
        </Modal.Footer>
      </div>
    </Modal>
  );
}
