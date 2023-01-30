import React from "react";
import { useEffect, useState } from "react";
import StoreApi from "../../../data/StoreRestApi";
import { Col, Container, Row } from "react-bootstrap";
import DealCard from "../../../components/Store/Deals/DealCard";
import { useLogin } from "../../../context/LoginContext";

const Deals = () => {
  const [dealTypes, setDealTypes] = useState([]);
  const { getAuthHeader, logged, openLogin } = useLogin();
  useEffect(() => {
    const api = new StoreApi();
    async function get() {
      let status;
      const result = await api
        .getDealTypes(getAuthHeader())
        .then((response) => {
          status = response.status;
          return response.json();
        })
        .catch((error) => console.error(error));
      if (status === 401) {
        openLogin();
      } else {
        setDealTypes(result);
      }
    }
    get();
  }, [logged]);

  return (
    <Container className="box-block mb-3 text-shadow-cls text-white">
      {/* <div className="d-flex justify-content-center">
        <h1 className="fs-2 text-white text-shadow-cls text-uppercase">
          Deals
        </h1>
      </div> */}
      <Row
        md={3}
        xs={1}
        lg={3}
        className="p-2 d-flex m-auto w-100"
        style={{ minHeight: "80vh" }}
      >
        {dealTypes.map((e) => (
          <Col xxl key={e} className="">
            <DealCard key={e} deal={e} />
          </Col>
        ))}
      </Row>
    </Container>
  );
};

export default Deals;
