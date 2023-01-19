import React from "react";
import { useState, useEffect } from "react";
import { Col, Row } from "react-bootstrap";
import ItemCard from "../ItemCard/ItemCard";
const StoreItems = ({ getItemsPage, isList }) => {
  const [items, setItems] = useState(isList ? [] : { content: [] });

  useEffect(() => {
    async function getItems() {
      setItems(await getItemsPage());
    }
    getItems();
    console.log(items);
  }, []);
  //TODO add display cards switcher( blocks, rows, small blocks etc)
  return (
    <Row md={2} xs={1} lg={3} className="g-3 m-auto">
      {(isList ? items : items.content).map((e) => (
        <Col key={e.id}>
          <ItemCard key={e.id} item={e} />
        </Col>
      ))}
    </Row>
  );
};

export default StoreItems;
