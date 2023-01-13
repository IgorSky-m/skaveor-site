import React from "react";
import { Link, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import ShopApi from "../../../services/shop/ShopRestService";

const Item = () => {
  const { itemId } = useParams();
  const [item, setItem] = useState();

  useEffect(() => {
    const api = new ShopApi();
    async function get() {
      setItem(await api.getItem(itemId));
    }

    get();
  }, []);
  console.log(item);
  return (
    <>
      {item && (
        <div className="store-item">
          <h4>Item card</h4>
          <h1>{item.title}</h1>
          <h5>{item.type}</h5>
          {item.categories &&
            item.categories.map((e) => {
              return (
                <Link to={`/store/categories/${e.id}`}>
                  {" "}
                  <div>categories: {e.name}</div>
                </Link>
              );
            })}
        </div>
      )}
    </>
  );
};

export default Item;
