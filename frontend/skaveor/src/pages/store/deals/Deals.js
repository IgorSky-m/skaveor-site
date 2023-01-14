import React from "react";
import { Link, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import StoreApi from "../../../services/store/StoreRestService";
import StoreItems from "../../../components/Shop/StoreItems/StoreItems";

const Deals = () => {
  const [dealTypes, setDealTypes] = useState([]);
  const [storeItems, setStoreItems] = useState();
  const [itemsVisibility, setItemsVisibility] = useState(false);
  const [dealCategory, setDealCategory] = useState("FLASH");
  const api = new StoreApi();

  useEffect(() => {
    async function getDealTypes() {
      setDealTypes(await api.getDealTypes());
    }

    setStoreItems(
      <StoreItems
        key={dealCategory}
        getItemsPage={() => api.getDealItems(dealCategory)}
      />
    );

    getDealTypes();
    console.log("render");
  }, [dealCategory]);

  const handleClick = (type) => {
    console.log("curent: " + dealCategory);
    console.log(type);
    setDealCategory((prev) => {
      if (prev !== type) {
        return type;
      }
      return prev;
    });
  };

  return (
    <>
      <div>
        {dealTypes.map((e) => {
          return (
            <Link onClick={() => handleClick(e)}>
              <div key={e}>{e}</div>
            </Link>
          );
        })}
      </div>
      <div>{storeItems}</div>
    </>
  );
};

export default Deals;
