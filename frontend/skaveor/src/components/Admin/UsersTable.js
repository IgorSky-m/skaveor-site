import React from "react";
import { Col, Row, Table } from "react-bootstrap";
import UserCard from "./UserCard";

const UsersTable = ({ users }) => {
  return (
    <Table className="text-light">
      <Row
        key="title"
        className=" text-uppercase fs-5 p-2 text-center d-flex justify-content-between gap-0 m-0 p-0 border border-secondary "
      >
        {/* <Col key={1}>id</Col> */}
        <Col key={2}>Date create</Col>
        <Col key={3}>Date last update</Col>
        <Col key={4}>Name</Col>
        <Col key={5}>Email</Col>
        <Col key={6}>Roles</Col>
        <Col key={7}>Status</Col>
      </Row>
      {users &&
        users.map((e) => {
          return <UserCard key={e.id} item={e} />;
        })}
    </Table>
  );
};

export default UsersTable;
