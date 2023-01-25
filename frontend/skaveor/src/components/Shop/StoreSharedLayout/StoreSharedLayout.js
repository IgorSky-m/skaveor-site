import React from "react";
import { Link, Outlet } from "react-router-dom";
import { ShoppingCartProvider } from "../../../context/ShoppingCartContext";
import StoreNavbar from "../StoreNavbar/StoreNavbar";
import StoreNavbarMini from "../StoreNavbar/StoreNavbarMini";

const StoreSharedLayout = () => {
  return (
    <ShoppingCartProvider>
      <StoreNavbarMini />
      <Outlet />
    </ShoppingCartProvider>
  );
};

export default StoreSharedLayout;
