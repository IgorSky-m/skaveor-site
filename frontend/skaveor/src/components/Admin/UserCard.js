import React, { useState } from "react";
import { Col, Row } from "react-bootstrap";
import { NavLink, useNavigate } from "react-router-dom";

const overClass = {
  backgroundColor: "#292b2c",
  cursor: "pointer",
};

const leaveClass = { cursor: "pointer" };

const UserCard = ({ item }) => {
  const [styles, setStyles] = useState(leaveClass);
  const navigate = useNavigate();
  const dtCreate = `${new Date(
    item.dt_create
  ).toLocaleDateString()}: ${new Date(item.dt_create).toLocaleTimeString()}`;

  const dtUpdate = `${new Date(
    item.dt_update
  ).toLocaleDateString()}: ${new Date(item.dt_update).toLocaleTimeString()}`;
  return (
    <Row
      key={item.id}
      onClick={() => navigate(item.id)}
      style={styles}
      onMouseOver={() => setStyles(overClass)}
      onMouseLeave={() => setStyles(leaveClass)}
      className="text-decoration-none text-white text-center d-flex justify-content-between gap-0 m-0 p-2 border-bottom border-secondary "
    >
      {/* <Col key={1}>
        {item.id}
      </Col> */}
      <Col key={2}>{dtCreate}</Col>
      <Col key={3}>{dtUpdate}</Col>
      <Col key={4}>{item.name}</Col>
      <Col key={5}>{item.email}</Col>
      <Col key={6}>{item.roles.join(", ")}</Col>
      <Col key={7}>{item.dt_delete ? "DEACTIVATED" : "ACTIVE"}</Col>
    </Row>
  );
};

export default UserCard;
