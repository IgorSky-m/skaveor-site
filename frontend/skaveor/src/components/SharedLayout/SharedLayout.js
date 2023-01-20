import React from "react";
import { Outlet } from "react-router-dom";
import "./SharedLayout.css";

import Header from "../Header/Header";
import Footer from "../Footer/Footer";
import { Container } from "react-bootstrap";

const SharedLayout = () => {
  const isTopOfPageNow = () => window.pageYOffset < 35;

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
    <Container className="p-0 m-0">
      <video autoPlay loop muted id="bg-video-2">
        <source
          id="bg-video-src-2"
          src="/video/scifi-13-1280.mp4"
          type="video/mp4"
        />
      </video>

      <Container
        className="content-sec m-auto"
        style={{
          // backgroundColor: "rgba(51, 53, 51, 0.5)",
          paddingTop: "80px",
        }}
      >
        <Outlet />
      </Container>
      <Header className="z-index-top" isTopOfPage={isTopOfPage} />
      <Footer />
    </Container>
  );
};

export default SharedLayout;
