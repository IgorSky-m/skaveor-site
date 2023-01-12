import React from "react";

import { Link, Outlet } from "react-router-dom";
import StoreNavbar from "../StoreNavbar/StoreNavbar";

const StoreSharedLayout = () => {
  return (
    <section className="store-section">
      <StoreNavbar />
      <Outlet />
    </section>
  );
};

export default StoreSharedLayout;
