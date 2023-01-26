import React, { useState } from "react";
import { Col, Row } from "react-bootstrap";

const overClass = {
  backgroundColor: "#292b2c",
  cursor: "pointer",
};

const leaveClass = { cursor: "pointer" };

const UserCard = ({ item }) => {
  const [styles, setStyles] = useState(leaveClass);

  return (
    <Row
      style={styles}
      onMouseOver={() => setStyles(overClass)}
      onMouseLeave={() => setStyles(leaveClass)}
      className="text-center d-flex justify-content-between gap-0 m-0 p-0 border-bottom border-secondary "
    >
      <Col>{item.id}</Col>
      <Col>{item.dt_create}</Col>
      <Col>{item.dt_update}</Col>
      <Col>{item.name}</Col>
      <Col>{item.email}</Col>
      <Col>{item.roles.join(", ")}</Col>
    </Row>
  );
};

export default UserCard;
