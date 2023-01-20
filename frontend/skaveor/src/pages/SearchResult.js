import React, { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import { useSearchParams } from "react-router-dom";
import StoreItemsPage from "../components/Shop/StoreItems/StoreItems";
import { useLogin } from "../context/LoginContext";
import StoreApi from "../data/store/StoreRestService";
const SearchResult = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const searchWord = searchParams.get("q");
  const api = new StoreApi();
  const { getAuthHeader } = useLogin();
  return (
    <Container className="box-block text-white text-shadow-cls mb-3 p-3 rounded-0">
      <div className="d-flex justify-content-center">
        <h1 className="fs-1 text-white text-uppercase">Search Results</h1>
      </div>
      <StoreItemsPage
        isList={true}
        getItemsPage={() =>
          api
            .searchItems(searchWord, getAuthHeader())
            .then((response) => response.json())
            .catch((error) => console.error(error))
        }
      />
    </Container>
  );
};

export default SearchResult;
