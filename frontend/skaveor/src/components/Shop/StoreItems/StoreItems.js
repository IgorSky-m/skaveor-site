import React from "react";
import { useState, useEffect } from "react";
import ItemCard from "../ItemCard/ItemCard";
const StoreItems = ({ getItemsPage }) => {
  const [items, setItems] = useState({ content: [] });

  useEffect(() => {
    async function getItems() {
      setItems(await getItemsPage());
    }
    getItems();
  }, []);
  //TODO add display cards switcher( blocks, rows, small blocks etc)
  return (
    <div className="store-items">
      {items.content.map((e) => {
        return <ItemCard key={e.id} item={e} />;
      })}
    </div>
  );
};

export default StoreItems;
