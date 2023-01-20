import React from "react";
import { Carousel, Container } from "react-bootstrap";

const VideoCarousel = ({ links = [], width = "400px", height = "200px" }) => {
  return (
    <Carousel fade className="" style={{ width: width }}>
      {links.map((e, i) => {
        if (e && e.src) {
          return (
            <Carousel.Item
              onSelect={() => e.handleSelect()}
              key={i}
              style={{ height: height }}
            >
              <video style={{ width: "100%" }} key={i} autoPlay loop muted>
                <source key={i} src={e.src} type="video/mp4" />
              </video>
              <Carousel.Caption>
                <h3
                  className="text-uppercase fs-4 text-shadow-cls"
                  style={{ fontFamily: "Skygraze" }}
                >
                  {e.label}
                </h3>
                <p className="text-shadow-cls">{e.description}</p>
              </Carousel.Caption>
            </Carousel.Item>
          );
        }
      })}
    </Carousel>
  );
};

export default VideoCarousel;
