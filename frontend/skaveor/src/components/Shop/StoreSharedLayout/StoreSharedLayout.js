import React from "react";
import { Link, Outlet } from "react-router-dom";
import { ShoppingCartProvider } from "../../../context/ShoppingCartContext";
import StoreNavbar from "../StoreNavbar/StoreNavbar";

const StoreSharedLayout = () => {
  return (
    <ShoppingCartProvider>
      <StoreNavbar />
      <Outlet />
    </ShoppingCartProvider>
  );
};

export default StoreSharedLayout;
