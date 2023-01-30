import React from "react";
import { useState, useEffect } from "react";
import { Col, Container, Row } from "react-bootstrap";
import StoreCategoryCard from "../../../components/Store/StoreCategoryCard/StoreCategoryCard";
import { useLogin } from "../../../context/LoginContext";
import StoreApi from "../../../data/StoreRestApi";
import wrapApiCall from "../../../data/ApiCallWrapper";
import { useNavigate } from "react-router-dom";
const StoreCategories = () => {
  const [categories, setCategories] = useState({ content: [] });
  const { getAuthHeader, openLogin, logged } = useLogin();
  const navigate = useNavigate();

  useEffect(() => {
    wrapApiCall(
      () => new StoreApi().getCategoriesPage(null, getAuthHeader()),
      (result) => setCategories(result),
      () => openLogin(),
      () => navigate("/forbidden")
    );
  }, [logged]);

  //TODO add display cards switcher( blocks, rows, small blocks etc)
  return (
    <Container className="box-block mb-3 d-flex flex-column justify-content-between text-shadow-cls">
      <div className="d-flex justify-content-center">
        <h1
          className="fs-1 text-white text-uppercase mt-2 text-shadow-cls"
          style={{ fontFamily: "Glitch" }}
        >
          Categories
        </h1>
      </div>
      <Row md={1} xs={1} lg={2} className="g-3 m-auto w-100 mb-3">
        {categories.content.map((e) => (
          <Col key={e.id}>
            <StoreCategoryCard key={e.id} item={e} />
          </Col>
        ))}
      </Row>
    </Container>
  );
};

export default StoreCategories;
