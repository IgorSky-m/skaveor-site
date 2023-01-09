import React from "react";
import { Outlet } from "react-router-dom";
import "./SharedLayout.css";

import Header from "../Header/Header";
import Footer from "../Footer/Footer";

const SharedLayout = () => {
  const isTopOfPageNow = () => window.pageYOffset === 0;

  const [isTopOfPage, setIsTopOfPage] = React.useState(isTopOfPageNow());

  const onScroll = (event) => {
    event.preventDefault();
    const isTopNow = isTopOfPageNow();
    if (isTopOfPage !== isTopNow) {
      setIsTopOfPage(isTopNow);
    }
  };

  React.useEffect(() => {
    window.addEventListener("scroll", onScroll, true);

    // Remove the event listener
    return () => {
      window.removeEventListener("scroll", onScroll, true);
    };
  }, [isTopOfPage]);

  return (
    <>
      <Header isTopOfPage={isTopOfPage} />
      <section id="content-section-wrapper" className="content-section-wrapper">
        <Outlet />
      </section>
      <Footer />
    </>
  );
};

export default SharedLayout;
