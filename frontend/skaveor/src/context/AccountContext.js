import { createContext, useContext, useState } from "react";
import Account from "../components/Account/Account";
import { useLogin } from "./LoginContext";

const AccountContext = createContext({
  openAccount: () => {},
  closeAccount: () => {},
});

export function useAccount() {
  return useContext(AccountContext);
}

export function AccountProvider({ children }) {
  const [isOpen, setIsOpen] = useState(false);
  const [payload, setPayload] = useState({ name: "", id: "", roles: [] });
  const [isAdmin, setIsAdmin] = useState(false);
  const { getPayload, logOut, openLogin } = useLogin();
  const openAccount = () => {
    async function getPl() {
      let status;
      const response = await getPayload()
        .then((resp) => {
          status = resp.status;
          return resp.json();
        })
        .catch((err) => console.error(err));
      if (status === 200) {
        setPayload(response);
        setIsOpen(true);
        setIsAdmin(response.roles.includes("ADMIN"));
      } else if (status === 401) {
        logOut();
        openLogin();
      }
    }
    getPl();
  };

  const closeAccount = () => setIsOpen(false);

  return (
    <AccountContext.Provider
      value={{
        openAccount,
        closeAccount,
        payload,
        isAdmin,
        isOpen,
      }}
    >
      {children}
      <Account isOpen={isOpen} />
    </AccountContext.Provider>
  );
}
