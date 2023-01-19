import React from "react";
import { useEffect, useState } from "react";
import StoreApi from "../../../data/store/StoreRestService";
import { Col, Container, Row } from "react-bootstrap";
import DealCard from "../../../components/Shop/Deals/DealCard";

const Deals = () => {
  const [dealTypes, setDealTypes] = useState([]);
  useEffect(() => {
    const api = new StoreApi();
    async function get() {
      setDealTypes(
        await api
          .getDealTypes()
          .then((response) => response.json())
          .catch((error) => console.error(error))
      );
    }
    get();
  }, []);

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
