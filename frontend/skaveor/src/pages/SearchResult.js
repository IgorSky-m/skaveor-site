import React, { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import { useSearchParams } from "react-router-dom";
import StoreItems from "../components/Shop/StoreItems/StoreItems";
import { useLogin } from "../context/LoginContext";
import StoreApi from "../data/store/StoreRestService";
const SearchResult = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const [search, setSearch] = useState("");

  const api = new StoreApi();
  const { getAuthHeader } = useLogin();

  useEffect(() => {
    const searchWord = searchParams.get("s");
    setSearch(searchWord);
  }, [search]);
  return (
    <Container className="box-block text-white text-shadow-cls mb-3 p-3 rounded-0">
      <div className="d-flex justify-content-center">
        <h1 className="fs-1 text-white text-uppercase">Search Results</h1>
      </div>
      <StoreItems
        isList={true}
        getItemsPage={() => api.searchItems(search, getAuthHeader())}
      />
    </Container>
  );
};

export default SearchResult;
