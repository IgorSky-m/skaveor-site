import React from "react";
import { useParams } from "react-router-dom";
import StoreApi from "../../../data/store/StoreRestService";
import { useState, useEffect } from "react";
import StoreItems from "../../../components/Shop/StoreItems/StoreItems";
import { Container } from "react-bootstrap";
const StoreCategory = () => {
  const { categoryId } = useParams();
  const [category, setCategory] = useState({});
  const api = new StoreApi();
  useEffect(() => {
    async function getOne() {
      setCategory(await api.getCategory(categoryId));
      return () => setCategory(null);
    }
    getOne();
  }, []);

  return (
    <Container className="box-block text-white text-shadow-cls mb-3 p-3 rounded-0">
      <div className="d-flex justify-content-center">
        <h1 className="fs-1 text-white text-uppercase">{category.name}</h1>
      </div>
      <StoreItems getItemsPage={() => api.getCategoryItemsPage(categoryId)} />
    </Container>
  );
};

export default StoreCategory;
