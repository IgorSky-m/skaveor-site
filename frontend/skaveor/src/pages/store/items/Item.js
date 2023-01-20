import React from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import StoreApi from "../../../data/store/StoreRestService";
import { Button, Container } from "react-bootstrap";
import ImageComponent from "../../../components/Img/ImageComponent";
import { useShoppingCart } from "../../../context/ShoppingCartContext";
import formatCurrency from "../../../utilities/formatCurrency";
import { useLogin } from "../../../context/LoginContext";
const Item = () => {
  const { itemId } = useParams();
  const [item, setItem] = useState();
  const { getItemQuantity, increaseItemQuantity, decreaseItemQuantity } =
    useShoppingCart();
  const { getAuthHeader, logged, openLogin } = useLogin();

  let navigate = useNavigate();

  const quantity = getItemQuantity(itemId);

  useEffect(() => {
    const api = new StoreApi();
    async function get() {
      let status;
      const result = await api
        .getItem(itemId, getAuthHeader())
        .then((response) => {
          status = response.status;
          return response.json();
        })
        .catch((error) => console.error(error));
      if (status === 401) {
        openLogin();
      } else {
        setItem(result);
      }
    }
    get();
  }, [logged]);

  return (
    <Container className="text-shadow-cls box-block  text-light">
      {item && (
        <Container
          fluid
          className="d-flex w-100"
          style={{ position: "relative" }}
        >
          <div style={{ position: "absolute", left: "60px", top: "30px" }}>
            {item.deals &&
              item.deals.map((e) => {
                if (e.type !== null) {
                  return (
                    <div
                      key={e.type}
                      className="bg-danger box-block light-anim-pulse text-light  p-1 mb-3 text-center"
                    >
                      {e.type}
                    </div>
                  );
                }
              })}
          </div>
          <div className="w-50 p-3">
            <ImageComponent title={item.title_picture} pics={item.pictures} />
          </div>
          <Container className=" d-flex flex-column justify-content-between align-items-center">
            <Container className="text-decoration-none d-flex flex-column justify-content-between align-items-center">
              <h1
                style={{ fontFamily: "Skygraze" }}
                className="fs-1 text-uppercase mt-2 text-shadow-cls"
              >
                {item.title}
              </h1>
              <div className="fs-4 mb-3 text-uppercase">{item.summary}</div>
              <h3 className="fs-5">Characteristics:</h3>
              <h3 className="fs-5">
                {item.characteristics && item.characteristics.description}
              </h3>
            </Container>

            <Container className="d-flex flex-column justify-content-between align-items-center">
              <div className="mb-2">
                <span className="fs-4">Available categories:</span>
              </div>
              {item.categories &&
                item.categories.map((e) => {
                  return (
                    <Link
                      key={e}
                      className="text-decoration-none text-white box-block p-2"
                      to={`/store/categories/${e.id}`}
                    >
                      <div>{e.name}</div>
                    </Link>
                  );
                })}
            </Container>
            <Container className="mb-4">
              <div className="d-flex justify-content-end fs-4 mb-3 text-shadow-cls">
                Price:{" "}
                <span className="fs-1" style={{ marginLeft: "10px" }}>
                  {formatCurrency(item.price)}
                </span>
              </div>
              <div className="mt-auto rounded-0 bg-dark p-1 light-anim">
                {quantity === 0 ? (
                  <Button
                    onClick={() => increaseItemQuantity(item.id)}
                    className="w-100 rounded-0 bg-dark -0 mt-1 border-0 "
                  >
                    + Add To Cart
                  </Button>
                ) : (
                  <div
                    className="d-flex justify-content-center flex-column bg-dark"
                    style={{ gap: ".5rem" }}
                  >
                    <div
                      className="d-flex align-items-center justify-content-center mt-1 mb-1"
                      style={{ gap: ".5rem" }}
                    >
                      <Button
                        onClick={() => decreaseItemQuantity(item.id)}
                        className="rounded-0 "
                        size="sm"
                        style={{
                          width: "2rem",
                          height: "2rem",
                          position: "relative",
                        }}
                        variant="outline-light"
                      >
                        -
                      </Button>
                      <div>
                        <span className="fs 5">{quantity}</span> in cart
                      </div>
                      <Button
                        onClick={() => increaseItemQuantity(item.id)}
                        className="rounded-0 border-secondary"
                        size="sm"
                        style={{
                          width: "2rem",
                          height: "2rem",
                          position: "relative",
                        }}
                        variant="outline-light"
                      >
                        +
                      </Button>
                    </div>
                  </div>
                )}
              </div>
              <div>
                <Button
                  onClick={() => {
                    navigate(-1);
                  }}
                  className="w-100 rounded-0 bg-secondary border-0 mt-1"
                >
                  Go Back
                </Button>
              </div>
            </Container>
          </Container>
        </Container>
      )}
    </Container>
  );
};

export default Item;
