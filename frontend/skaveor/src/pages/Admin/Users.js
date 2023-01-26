import React, { useEffect, useState } from "react";
import { Col, Container, Row, Table } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import UserCard from "../../components/Admin/UserCard";
import { useLogin } from "../../context/LoginContext";
import AccountApi from "../../data/AccountApi";

const Users = () => {
  const [usersPage, setUsersPage] = useState({ content: [] });
  const { getAuthHeader } = useLogin();
  const [pageIndex, setPageIndex] = useState(0);
  const navigate = useNavigate();
  useEffect(() => {
    const api = new AccountApi();

    async function getUsersPage() {
      let status;
      const result = await api
        .getAllPage(getAuthHeader(), {
          page: pageIndex,
          size: 30,
          field: "dt_create",
          direction: "ASC",
        })
        .then((response) => {
          status = response.status;
          return response.json();
        })
        .catch((err) => console.error(err));

      if (status === 200) {
        setUsersPage(result);
      } else if (status === 403) {
        navigate("forbidden");
      }
    }

    getUsersPage();
    console.log(usersPage);
    return () => {};
  }, [pageIndex]);

  return (
    <>
      <Container className="text-center p-2">
        <h1 className="fs-3">Users</h1>
      </Container>
      {usersPage && (
        <Table className="text-light">
          <Row className=" text-uppercase fs-5 p-2 text-center d-flex justify-content-between gap-0 m-0 p-0 border border-secondary ">
            <Col>id</Col>
            <Col>Date create</Col>
            <Col>Date last update</Col>
            <Col>Name</Col>
            <Col>Email</Col>
            <Col>Roles</Col>
          </Row>
          {usersPage.content.map((e) => {
            return <UserCard item={e} />;
          })}
        </Table>
      )}
    </>
  );
};

export default Users;
