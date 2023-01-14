import React from "react";
import { useParams } from "react-router-dom";
import StoreApi from "../../../services/store/StoreRestService";
import { useState, useEffect } from "react";
import StoreItems from "../../../components/Shop/StoreItems/StoreItems";
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
    <div className="store-category-detailed-view">
      <h6>Category card</h6>
      <h5>{category.id}</h5>
      <h1>{category.name}</h1>
      <h3>{category.description}</h3>
      <p>Caregory items:</p>
      <StoreItems getItemsPage={() => api.getCategoryItemsPage(categoryId)} />
    </div>
  );
};

export default StoreCategory;
