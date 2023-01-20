import React from "react";
import { Carousel, Container } from "react-bootstrap";

const ImageCarousel = ({ links = [] }) => {
  return (
    <Container className="border">
      <Carousel>
        {links.map((e) => (
          <Carousel.Item key={e}>
            <img key={e} src={e} alt={e} />
          </Carousel.Item>
        ))}
      </Carousel>
    </Container>
  );
};

export default ImageCarousel;
