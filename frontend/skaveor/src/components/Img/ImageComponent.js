import React, { useEffect, useState } from "react";
import { Col, Container, Row } from "react-bootstrap";

function ImageComponent({ title, pics }) {
  const [imgIndex, setImgIndex] = useState(0);
  const [images, setImages] = useState([]);

  useEffect(() => {
    setImages([title, ...pics]);
  }, []);

  const changeIndex = (index) => {
    if (imgIndex !== index) {
      setImgIndex(index);
    }
  };

  return (
    <>
      <Container className="mb-2 box-block rounded-0">
        <Row className="rounded-0 p-2">
          <Col
            className="m-auto d-flex justify-content-md-center rounded-0"
            style={{ height: "450px", width: "550px" }}
          >
            <img className="" src={images[imgIndex]} />
          </Col>
        </Row>
        <Row className="rounded-0 box-block">
          {images.map((e, i) => {
            return (
              <Col
                key={i}
                style={{ height: "100px", width: "100px", coursore: "" }}
                onClick={() => changeIndex(i)}
                className="rounded-0 m-0 p-0 m-auto d-flex justify-content-md-around card-block"
              >
                <img
                  className="rounded-0 p-1"
                  style={{ height: "100px", width: "100px" }}
                  key={e}
                  src={e}
                  alt={e}
                />
              </Col>
            );
          })}
        </Row>
      </Container>
    </>
  );
}

export default ImageComponent;
