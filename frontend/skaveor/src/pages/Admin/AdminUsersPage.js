import React, { useEffect, useState } from "react";
import { Col, Container, Row, Table } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import UsersTable from "../../components/Admin/UsersTable";
import { useLogin } from "../../context/LoginContext";
import AccountApi from "../../data/AccountApi";
import wrapApiCall from "../../data/ApiCallWrapper";

const AdminUsersPage = () => {
  const [usersPage, setUsersPage] = useState({ content: [] });
  const { getAuthHeader, logOut, openLogin } = useLogin();
  const [pageIndex, setPageIndex] = useState(0);
  const navigate = useNavigate();

  useEffect(() => {
    wrapApiCall(
      () =>
        new AccountApi().getAllPage(getAuthHeader(), {
          page: pageIndex,
          size: 30,
          field: "dt_create",
          direction: "ASC",
        }),
      (result) => setUsersPage(result),
      () => {
        logOut();
        openLogin(false);
      },
      () => navigate("forbidden")
    );
  }, [pageIndex]);

  return (
    <>
      <Container className="text-center p-2">
        <h1 className="fs-3">Users</h1>
      </Container>
      {usersPage && <UsersTable users={usersPage.content} />}
    </>
  );
};

export default AdminUsersPage;
