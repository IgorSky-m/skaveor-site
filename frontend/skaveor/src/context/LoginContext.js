import { createContext, useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import Login from "../components/Login/Login";
import Signup from "../components/Login/Signup";
import AuthApi from "../data/AuthApi";
import { useLocalStorage } from "../hooks/useLocalStorage";
import { useShoppingCart } from "./ShoppingCartContext";

const LoginContext = createContext({
  logOut: () => {},
  openLogin: (goBack = true) => {},
  openRegister: (goBack = true) => {},
  close: () => {},
  isLogged: () => false,
  logIn: (token) => {},
  loginCheck: () => {},
  getAuthHeader: () => {
    return { Authorization: "" };
  },
});

export function useLogin() {
  return useContext(LoginContext);
}

export function LoginProvider({ children }) {
  const [token, setToken] = useLocalStorage("Token", { Bearer: "" });
  const [isLoginOpen, setIsLoginOpen] = useState(false);
  const [isRegisterOpen, setIsRegisterOpen] = useState(false);
  const [goBack, setGoBack] = useState(true);
  const [logged, setLogged] = useState(token.Bearer !== "");
  const api = new AuthApi();

  const logIn = async (token) => {
    setToken({ Bearer: token });
    setLogged(true);
  };
  const openLogin = (goBack = true) => {
    setGoBackOption(goBack);
    setIsLoginOpen(true);
    if (isRegisterOpen) {
      setIsRegisterOpen(false);
    }
  };
  const openRegister = (goBack = true) => {
    setGoBackOption(goBack);
    setIsRegisterOpen(true);
    if (isLoginOpen) {
      setIsLoginOpen(false);
    }
  };

  const getAuthHeader = () => {
    return {
      Authorization: token.Bearer,
    };
  };

  const setGoBackOption = (goBack) => {
    setGoBack((prev) => (prev === goBack ? prev : goBack));
  };

  const loginCheck = async (goBack = true) => {
    if ((await isLogged()) === false) {
      openLogin(goBack);
    }
  };

  const close = async () => {
    setIsLoginOpen((prev) => (prev !== false ? false : prev));
    setIsRegisterOpen((prev) => (prev !== false ? false : prev));
  };

  const isLogged = async () =>
    await api
      .validateToken(token.Bearer)
      .then((response) => {
        const valid = response.status === 200;
        setLogged(valid);
        return valid;
      })
      .catch((ex) => {
        return false;
      });

  const logOut = (navigate = () => {}) => {
    setToken({ Bearer: "" });
    setLogged(false);

    navigate();
  };

  return (
    <LoginContext.Provider
      value={{
        openLogin,
        close,
        openRegister,
        isLogged,
        logIn,
        logOut,
        loginCheck,
        getAuthHeader,
        logged,
      }}
    >
      {children}
      <Login show={isLoginOpen} onHide={close} goBack={goBack} />
      <Signup show={isRegisterOpen} onHide={close} goBack={goBack} />
    </LoginContext.Provider>
  );
}
