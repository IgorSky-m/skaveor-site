import React, { useState } from "react";
import { Button, Card, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

const ShopNewsCard = ({ item }) => {
  const [bodyStyle, setBodyStyle] = useState({});
  const [titleStyle, settitleStyle] = useState({ fontFamily: "Glitch" });
  const [descriptionStyle, setDescriptionStyle] = useState({
    position: "absolute",
    width: "300px",
    opacity: "0",
  });

  const getLink = () => {
    if (item.redirect_to_category) {
      return `/store/categories/${item.redirect_to_category}`;
    }

    if (item.redirect_to_item) {
      return `/store/items/${item.redirect_to_item}`;
    }
    if (item.redirect_to_deal) {
      return `/store/deals/${item.redirect_to_deal}`;
    }
    return "/news";
  };

  const mouseOver = (e) => {
    settitleStyle((prev) => {
      return {
        ...prev,
        transform: "translate(0px, -80px)",
        opacity: "1",
        transition: "1s transform, 1s opacity",
      };
    });

    setBodyStyle((prev) => {
      return {
        ...prev,
        backgroundColor: "rgba(0, 0, 0, 0.5)",
        transition: "0.5s background-color",
      };
    });

    setDescriptionStyle((prev) => {
      return {
        ...prev,
        transform: "translate(0px, 100px)",
        opacity: "1",
        transition: "1s transform, 1.5s opacity",
      };
    });
  };

  const mouseLeave = (e) => {
    settitleStyle((prev) => {
      return {
        ...prev,
        transform: "translate(0px, 0px)",
        opacity: "0.8",
        transition: "1s transform, 1s opacity",
      };
    });

    setBodyStyle((prev) => {
      return {
        ...prev,
        backgroundColor: "rgba(0, 0, 0, 0)",
        transition: "1s background-color",
      };
    });

    setDescriptionStyle((prev) => {
      return {
        ...prev,
        transform: "translate(0px, 0px)",
        opacity: "0",
        transition: "1s transform, 0.5s opacity",
      };
    });
  };

  const titlePic = item.images
    ? item.images.find((e) => e.image_type === "TITLE")
    : null;
  return (
    <Card
      as={Link}
      to={getLink()}
      className="h-100 bg-dark text-light rounded-0"
      style={{ position: "relative" }}
    >
      <Card.Img
        className="rounded-0"
        variant="top"
        src={titlePic ? titlePic.src : ""}
        height="550px"
        style={{ objectFit: "cover" }}
      />

      <Card.Body
        onMouseEnter={mouseOver}
        onMouseLeave={mouseLeave}
        className="text-appear rounded-0 d-flex flex-column align-items-center justify-content-center text-shadow-cls card-img-overlay border-0"
        style={bodyStyle}
      >
        <Card.Title
          style={titleStyle}
          className="text-uppercase mb-4 text-center"
        >
          <span className="fs-2 text-uppercase ">{item.title}</span>
        </Card.Title>
        <Card.Text style={descriptionStyle} className="fs-4 text-center">
          {item.summary}
        </Card.Text>
      </Card.Body>
    </Card>
  );
};

export default ShopNewsCard;
