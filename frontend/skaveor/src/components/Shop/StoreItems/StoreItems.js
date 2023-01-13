import React from "react";
import { useState, useEffect } from "react";
import ShopApi from "../../../services/shop/ShopRestService";
import ItemCard from "../ItemCard/ItemCard";
const StoreItems = ({ category }) => {
  const [items, setItems] = useState({ content: [] });

  const api = new ShopApi();

  useEffect(() => {
    async function getItems() {
      setItems(await api.getCategoryItemsPage(category));
    }
    getItems();
  }, []);
  //TODO add display cards switcher( blocks, rows, small blocks etc)
  console.log(items.content);
  return (
    <div className="store-items">
      {items.content.map((e) => {
        return <ItemCard key={e.id} item={e} />;
      })}
    </div>
  );
};

export default StoreItems;
