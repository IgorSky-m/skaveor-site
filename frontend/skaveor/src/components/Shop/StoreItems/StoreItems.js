import React from "react";
import { useState, useEffect } from "react";
import { Col, Row } from "react-bootstrap";
import { useLogin } from "../../../context/LoginContext";
import ItemCard from "../ItemCard/ItemCard";
const StoreItems = ({ getItemsPage, isList, secured = false }) => {
  const [items, setItems] = useState([]);
  const { logged, openLogin } = useLogin();

  useEffect(() => {
    async function getItems() {
      let status;
      const items = await getItemsPage()
        .then((response) => {
          status = response.status;
          return response.json();
        })
        .catch((error) => console.error(error));

      if (secured && status === 401) {
        openLogin();
      } else {
        const resultItems = Array.isArray(items) ? items : items.content;
        setItems(resultItems);
      }
    }
    getItems();
  }, [logged]);
  //TODO add display cards switcher( blocks, rows, small blocks etc)
  return (
    <Row md={2} xs={1} lg={3} className="g-3 m-auto">
      {items.map((e) => (
        <Col key={e.id}>
          <ItemCard key={e.id} item={e} />
        </Col>
      ))}
    </Row>
  );
};

export default StoreItems;
