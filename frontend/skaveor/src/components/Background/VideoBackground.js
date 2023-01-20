import React from "react";
import { Outlet } from "react-router-dom";
import "./VideoBackground.css";
const VideoBackground = () => {
  return (
    <>
      <Outlet className="z-index-top" />
      <video autoPlay loop muted id="bg-video">
        <source
          className="m-0 p-0"
          id="bg-video-src"
          src="/video/bg-floor.mp4"
          type="video/mp4"
        />
      </video>
    </>
  );
};

export default VideoBackground;
