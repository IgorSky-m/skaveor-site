import React from "react";
import { useState, useEffect } from "react";
import StoreCategoryCard from "../../../components/Shop/StoreCategoryCard/StoreCategoryCard";
import StoreApi from "../../../services/store/StoreRestService";

const StoreCategories = () => {
  const [categories, setCategories] = useState({ content: [] });

  useEffect(() => {
    const api = new StoreApi();
    async function get() {
      setCategories(await api.getCategoriesPage());
    }
    get();
  }, []);
  //TODO add display cards switcher( blocks, rows, small blocks etc)
  return (
    <>
      <h3>Categories</h3>
      {categories.content.map((e) => {
        return <StoreCategoryCard key={e.id} item={e} />;
      })}
    </>
  );
};

export default StoreCategories;
