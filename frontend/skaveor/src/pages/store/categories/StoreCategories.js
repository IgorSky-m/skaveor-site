import React from "react";
import { useState, useEffect } from "react";
import StoreCategoryCard from "../../../components/StoreCategoryCard/StoreCategoryCard";
import ShopApi from "../../../services/shop/ShopRestService";

const StoreCategories = () => {
  const api = new ShopApi();

  const [categories, setCategories] = useState([]);

  useEffect(() => {
    async function get() {
      setCategories(await api.getCategories());
    }
    get();
  }, []);
  //TODO add display cards switcher( blocks, rows, small blocks etc)
  return (
    <>
      <h3>Categories</h3>
      {categories.map((e) => {
        return <StoreCategoryCard key={e.id} item={e} />;
      })}
    </>
  );
};

export default StoreCategories;
