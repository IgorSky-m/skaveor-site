import React from "react";
import { useParams } from "react-router-dom";
import StoreApi from "../../../data/store/StoreRestService";
import { useState, useEffect } from "react";
import StoreItems from "../../../components/Shop/StoreItems/StoreItems";
import { Container } from "react-bootstrap";
import { useLogin } from "../../../context/LoginContext";
const StoreCategory = () => {
  const { categoryId } = useParams();
  const [category, setCategory] = useState({});
  const { getAuthHeader, logged, openLogin } = useLogin();
  const api = new StoreApi();
  useEffect(() => {
    async function getOne() {
      let status;
      const result = await api
        .getCategory(categoryId, getAuthHeader())
        .then((response) => {
          status = response.status;
          return response.json();
        })
        .catch((error) => console.error(error));
      if (status === 401) {
        openLogin();
      } else {
        setCategory(result);
      }
    }
    getOne();
  }, [logged]);

  return (
    <Container className="box-block text-white text-shadow-cls mb-3 p-3 rounded-0">
      <div className="d-flex justify-content-center">
        <h1 className="fs-1 text-white text-uppercase">{category.name}</h1>
      </div>
      <StoreItems
        getItemsPage={() =>
          api.getCategoryItemsPage(categoryId, getAuthHeader())
        }
      />
    </Container>
  );
};

export default StoreCategory;
