import React, { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import { useSearchParams } from "react-router-dom";
import { useLogin } from "../context/LoginContext";
import StoreApi from "../data/StoreRestApi";
import wrapApiCall from "../data/ApiCallWrapper";
import ItemsBlock from "../components/Store/StoreItems/ItemsBlock";
const SearchResult = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const [content, setContent] = useState([]);

  const { getAuthHeader } = useLogin();

  useEffect(() => {
    const api = new StoreApi();

    wrapApiCall(
      () => api.searchItems(searchParams.get("s"), getAuthHeader()),
      setContent
    );
  }, [searchParams]);

  return (
    <Container className="box-block text-white text-shadow-cls mb-3 p-3 rounded-0">
      <div className="d-flex justify-content-center">
        <h1 className="fs-1 text-white text-uppercase">Search Results</h1>
      </div>

      <ItemsBlock items={content} />
    </Container>
  );
};

export default SearchResult;
