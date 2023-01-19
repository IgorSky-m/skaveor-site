import React, { useEffect, useState } from "react";
import { Col, Container, Row } from "react-bootstrap";
import { useParams } from "react-router-dom";
import OrderApi from "../../data/OrderRestApi";
const OrderDetails = () => {
  const { orderId } = useParams();
  const [order, setOrder] = useState({});
  useEffect(() => {
    const api = new OrderApi();
    async function getOrder() {
      setOrder(
        await api
          .getOrderDetails(orderId)
          .then((response) => response.json())
          .catch((error) => console.error(error))
      );
    }

    getOrder();
  }, []);

  return (
    <Container>
      <Row className="text-white fs-2 justify-content-md-center">
        <Col>Order Placed</Col>
      </Row>
    </Container>
  );
};

export default OrderDetails;
