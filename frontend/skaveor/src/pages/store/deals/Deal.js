import React from "react";
import { useParams } from "react-router-dom";
import StoreApi from "../../../data/StoreRestApi";
import StoreItems from "../../../components/Store/StoreItems/StoreItems";
import { Container } from "react-bootstrap";
import { useLogin } from "../../../context/LoginContext";

const Deal = () => {
  const { dealType } = useParams();
  const api = new StoreApi();
  const { getAuthHeader } = useLogin();
  return (
    <Container className="box-block text-white text-shadow-cls mb-3 p-3 rounded-0">
      <div className="d-flex justify-content-center">
        <h1
          className="fs-1 text-white text-uppercase"
          style={{ fontFamily: "Glitch" }}
        >
          {dealType}
        </h1>
      </div>
      <StoreItems
        secured={true}
        getItemsPage={() => api.getDealItems(dealType, null, getAuthHeader())}
      />
    </Container>
  );
};

export default Deal;
