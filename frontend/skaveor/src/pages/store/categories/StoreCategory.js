import React from "react";
import { useParams } from "react-router-dom";
import ShopApi from "../../../services/shop/ShopRestService";
import { useState, useEffect } from "react";
const StoreCategory = () => {
  const { categoryId } = useParams();
  const [category, setCategory] = useState({});

  const api = new ShopApi();

  useEffect(() => {
    async function getOne() {
      setCategory(await api.getCategory(categoryId));
    }
    getOne();
  }, []);

  return (
    <div className="store-category-detailed-view">
      <h5>{category.id}</h5>
      <h1>{category.name}</h1>
      <h3>{category.description}</h3>
    </div>
  );
};

export default StoreCategory;
