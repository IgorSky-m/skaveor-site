import React from "react";
import { useNavigate, useParams } from "react-router-dom";
import StoreApi from "../../../data/StoreRestApi";
import { useState, useEffect } from "react";
import StoreItems from "../../../components/Store/StoreItems/StoreItems";
import { Container } from "react-bootstrap";
import { useLogin } from "../../../context/LoginContext";
import wrapApiCall from "../../../data/ApiCallWrapper";
import ItemsBlock from "../../../components/Store/StoreItems/ItemsBlock";
const StoreCategory = () => {
  const { categoryId } = useParams();
  const [category, setCategory] = useState({});
  const { getAuthHeader, logged, openLogin } = useLogin();
  const [loading, setLoading] = useState(false);
  const [itemsPage, setItemsPage] = useState({
    content: [],
    empty: true,
    first: true,
    last: true,
    total_elements: 0,
    total_pages: 0,
    size: 0,
    number: 0,
    number_of_elements: 20,
  });
  const api = new StoreApi();
  const navigate = useNavigate();

  useEffect(() => {
    setLoading(true);
    //call category
    wrapApiCall(
      () => api.getCategory(categoryId, getAuthHeader()),
      setCategory,
      openLogin,
      () => navigate("/forbidden")
    );
    //call this category items page
    wrapApiCall(
      () => api.getCategoryItemsPage(categoryId, getAuthHeader()),
      setItemsPage,
      openLogin,
      () => navigate("/forbidden")
    );
    setLoading(false);
  }, [logged]);

  return (
    <Container className="box-block text-white text-shadow-cls mb-3 p-3 rounded-0">
      <div className="d-flex justify-content-center">
        <h1
          className="fs-1 text-white text-uppercase"
          style={{ fontFamily: "Glitch" }}
        >
          {category.name}
        </h1>
      </div>
      <ItemsBlock items={itemsPage.content} filterProperty="title" />
    </Container>
  );
};

export default StoreCategory;
