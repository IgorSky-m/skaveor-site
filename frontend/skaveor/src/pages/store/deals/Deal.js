import React from "react";
import { useParams } from "react-router-dom";
import StoreApi from "../../../data/store/StoreRestService";
import { useState, useEffect } from "react";
import StoreItems from "../../../components/Shop/StoreItems/StoreItems";
import { Container } from "react-bootstrap";

const Deal = () => {
  const { dealType } = useParams();
  const api = new StoreApi();

  return (
    <Container className="box-block text-white text-shadow-cls mb-3 p-3 rounded-0">
      <div className="d-flex justify-content-center">
        <h1 className="fs-1 text-white text-uppercase">{dealType}</h1>
      </div>
      <StoreItems
        getItemsPage={() =>
          api
            .getDealItems(dealType)
            .then((response) => response.json())
            .catch((error) => console.error(error))
        }
      />
    </Container>
  );
};

export default Deal;
