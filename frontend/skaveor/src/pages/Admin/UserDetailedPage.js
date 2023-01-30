import React, { useEffect, useState } from "react";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Button, Col, Container, Dropdown, Form, Row } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import { useLogin } from "../../context/LoginContext";
import AccountApi from "../../data/AccountApi";
import { AVAILABLE_USER_ROLES } from "../../data/Constraints";
import wrapApiCall from "../../data/ApiCallWrapper";

const UserDetailedPage = () => {
  const { userId } = useParams();
  const { getAuthHeader, logOut, openLogin } = useLogin();
  const navigate = useNavigate();

  const [user, setUser] = useState({
    id: "",
    name: "",
    password: "",
    email: "",
    roles: [],
  });
  const [roles, setRoles] = useState([]);
  const [password, setPassword] = useState("");
  const [isPassUpdateEnable, setPassUpdateEnable] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const api = new AccountApi();
  useEffect(() => {
    wrapApiCall(
      () => api.getOne(userId, getAuthHeader()),
      (result) => {
        setUser(result);
        setRoles(result.roles);
      },
      () => {
        logOut();
        openLogin(false);
      },
      () => {
        navigate("/forbidden");
      }
    );

    async function getUser() {
      let status;
      const result = await api
        .getOne(userId, getAuthHeader())
        .then((response) => {
          status = response.status;
          return response.json();
        })
        .catch((err) => console.error(err));

      if (status === 200) {
        setUser(result);
        setRoles(result.roles);
      } else if (status === 401) {
        logOut();
        openLogin(false);
      } else if (status === 403) {
        navigate("/forbidden");
      }
    }

    getUser();

    return () => {};
  }, []);

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setUser((prev) => {
      return { ...prev, [name]: value };
    });
  };

  const updateUser = async (navigateIfSuccess = false, navigatePath = -1) => {
    let resulPassword = isPassUpdateEnable ? password : "";
    let status;
    const result = await api
      .update(
        userId,
        user.dt_update,
        { ...user, roles: roles, password: resulPassword },
        getAuthHeader()
      )
      .then((resp) => {
        status = resp.status;
        return resp.json();
      })
      .catch((err) => {
        console.error(err);
        toast.error("Runtime error");
      });

    if (status === 200) {
      toast.success("Successfull");
      setUser(result);
      setRoles(result.roles);
      setPassword("");
      if (navigateIfSuccess === true) {
        navigate(navigatePath);
      }
    } else if (status === 401) {
      toast.warning("Unauthorized", {
        position: toast.POSITION.BOTTOM_LEFT,
        autoClose: 15000,
      });
    } else if (status === 403) {
      navigate("forbidden");
    }
  };

  const activate = async () => {
    let status;
  };
  const deactivate = async () => {};

  return (
    <Container
      className="text-white text-center p-2 border h-100"
      style={{ position: "relative" }}
    >
      <ToastContainer
        position="top-right"
        autoClose={500}
        hideProgressBar={false}
        newestOnTop
        closeOnClick
        rtl={false}
        pauseOnFocusLoss={false}
        draggable
        pauseOnHover
        theme="dark"
      />
      <Form autoComplete="off" className=" text-center">
        <Form.Group
          className=" mt-3 m-2 mb-4 text-center m-auto w-75 d-flex align-items-center gap-0"
          controlId="id"
        >
          <Form.Label className="fs-5 w-25">ID</Form.Label>
          <Form.Control
            disabled
            type="text"
            placeholder="id"
            autoComplete="off"
            name="id"
            value={user.id}
            onChange={handleChange}
            className={`bg-dark rounded-0 text-secondary p-3 input-drk w-75 border-secondary`}
            style={{ position: "relative" }}
          />
        </Form.Group>
        <Form.Group
          className=" mt-3 m-2 mb-4 text-center m-auto w-75 d-flex align-items-center gap-0"
          controlId="name"
        >
          <Form.Label className="fs-5 w-25 ">NAME</Form.Label>
          <Form.Control
            type="text"
            placeholder="Name"
            autoComplete="off"
            name="name"
            value={user.name}
            onChange={handleChange}
            className={`bg-dark rounded-0 text-white p-3 input-drk w-75`}
            style={{ position: "relative" }}
          />
        </Form.Group>
        <Form.Group
          className=" mt-3 m-2 mb-4 text-center m-auto w-75 d-flex align-items-center gap-0"
          controlId="email"
        >
          <Form.Label className="fs-5 w-25 ">EMAIL</Form.Label>
          <Form.Control
            type="email"
            placeholder="Email address"
            autoFocus
            autoComplete="off"
            name="email"
            value={user.email}
            onChange={handleChange}
            className={`bg-dark rounded-0 text-white p-3 input-drk w-75`}
            style={{ position: "relative" }}
          />
        </Form.Group>
        <Form.Group
          className=" mt-3 m-2 mb-4 text-center m-auto w-75 d-flex align-items-center gap-0"
          controlId="password"
          style={{ position: "relative" }}
        >
          <Form.Label
            className={`fs-5 w-25 d-flex gap-3 m-0 p-0 ${
              isPassUpdateEnable ? "text-white" : "text-secondary"
            }`}
          >
            PASSWORD
            <Form.Group controlId="passUpdateCheck">
              <Form.Check
                type="checkbox"
                onChange={() => {}}
                checked={isPassUpdateEnable}
                onClick={() => setPassUpdateEnable((prev) => !prev)}
              />
            </Form.Group>
          </Form.Label>
          <Form.Control
            disabled={!isPassUpdateEnable}
            type={showPassword ? "text" : "password"}
            placeholder="New password"
            autoFocus
            autoComplete="off"
            name="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className={`bg-dark rounded-0 p-3 input-drk w-75 ${
              isPassUpdateEnable
                ? "text-white"
                : "text-secondary border-secondary"
            }`}
          />

          <Form.Group
            controlId="showPasswordId"
            className="m-0 p-0"
            style={{
              position: "absolute",
              right: "0px",
              width: "20%",
              height: "100%",
            }}
          >
            <Form.Check
              type="checkbox"
              className={`border d-flex align-items-center justify-content-center gap-2 m-0 p-4 ${
                isPassUpdateEnable ? "" : "text-secondary border-secondary"
              }`}
              checked={showPassword}
              onChange={() => {}}
              disabled={!isPassUpdateEnable}
              onClick={() => setShowPassword((prev) => !prev)}
              label="Show password"
              style={{
                height: "100%",
              }}
            />
          </Form.Group>
        </Form.Group>
        <Form.Group
          className=" mt-3 m-2 mb-4 text-center m-auto w-75 d-flex align-items-center gap-0"
          controlId="email"
        >
          <Form.Label className="fs-5 w-25 ">ROLES</Form.Label>
          <Row className="d-flex align-items-center gap-3">
            {roles.map((e, i) => {
              return (
                <Col
                  as={Button}
                  variant="outline-secondary"
                  onClick={() => {
                    setRoles((prev) => {
                      return prev.filter((role) => e !== role);
                    });
                  }}
                  key={i}
                  className="rounded-0 m-0 text-white text-align-center"
                >
                  {e}
                </Col>
              );
            })}
            <Col className="rounded-0 m-0 text-white text-align-center">
              <Dropdown className="m-0">
                <Dropdown.Toggle
                  className="no-arrow-dropdown rounded-0"
                  variant="outline-secondary"
                  id="dropdown-basic"
                >
                  +
                </Dropdown.Toggle>

                <Dropdown.Menu variant="dark rounded-0">
                  {AVAILABLE_USER_ROLES.map((e) => (
                    <Dropdown.Item
                      onClick={() => {
                        if (!roles.includes(e)) {
                          setRoles((prev) => [...prev, e]);
                        }
                      }}
                      key={e}
                    >
                      {e}
                    </Dropdown.Item>
                  ))}
                </Dropdown.Menu>
              </Dropdown>
            </Col>
          </Row>
        </Form.Group>
      </Form>

      <Button
        variant="outline-secondary"
        onClick={() => navigate(-1)}
        className="rounded-0"
        style={{
          position: "absolute",
          bottom: "60px",
          right: "10px",
          width: "100px",
        }}
      >
        Go back
      </Button>

      <Button
        variant="outline-success"
        onClick={updateUser}
        className="rounded-0"
        style={{
          position: "absolute",
          bottom: "60px",
          right: "120px",
          width: "100px",
        }}
      >
        Update
      </Button>
      <Button
        variant="outline-primary"
        onClick={() => updateUser(true)}
        className="rounded-0 border-white text-white"
        style={{
          position: "absolute",
          bottom: "60px",
          right: "230px",
          width: "100px",
        }}
      >
        OK
      </Button>
      {user.dt_delete ? (
        <Button
          variant="outline-success"
          onClick={activate}
          className="rounded-0"
          style={{
            position: "absolute",
            bottom: "60px",
            right: "340px",
            width: "100px",
          }}
        >
          Activate
        </Button>
      ) : (
        <Button
          variant="outline-danger"
          onClick={deactivate}
          className="rounded-0"
          style={{
            position: "absolute",
            bottom: "60px",
            right: "340px",
            width: "100px",
          }}
        >
          Deactivate
        </Button>
      )}
    </Container>
  );
};

export default UserDetailedPage;
