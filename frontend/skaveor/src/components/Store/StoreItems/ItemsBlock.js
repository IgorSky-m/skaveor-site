import React from "react";
import { useState, useEffect } from "react";
import { Col, Container, Row } from "react-bootstrap";

import ItemCard from "../ItemCard/ItemCard";
const ItemsBlock = ({ items, filterProperty = null }) => {
  const [filterValue, setFilterValue] = useState("");

  const filteredItems = () => {
    if (!filterProperty) {
      return items;
    }
    return items.filter((e) =>
      e[filterProperty].toLowerCase().includes(filterValue.toLocaleLowerCase())
    );
  };
  const handleChange = (event) => {
    setFilterValue(event.target.value);
  };
  //TODO add display cards switcher( blocks, rows, small blocks etc)
  return (
    <Container className="mt-2">
      {filterProperty && (
        <Row className="m-auto">
          <input
            className="rounded-0 pa3 bb br3 grow b--none bg-lightest-blue ma3"
            type="search"
            placeholder="FIlter"
            onChange={handleChange}
          />
        </Row>
      )}
      <Row md={2} xs={1} lg={3} className="g-3 m-auto">
        {filteredItems().map((e) => (
          <Col key={e.id}>
            <ItemCard key={e.id} item={e} />
          </Col>
        ))}
      </Row>
    </Container>
  );
};

export default ItemsBlock;
