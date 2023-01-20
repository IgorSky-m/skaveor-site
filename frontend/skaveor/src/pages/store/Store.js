import React, { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import NewsApi from "../../data/NewsApi";
import ShopNewsCard from "../../components/Shop/ShopNewsCard";
const Store = () => {
  const [news, setNews] = useState([]);

  const isPromo = (item) => {
    return (
      item.redirect_to_category ||
      item.redirect_to_item ||
      item.redirect_to_deal
    );
  };

  useEffect(() => {
    const api = new NewsApi();

    async function getNews() {
      const result = await api
        .getNews()
        .then((resp) => resp.json())
        .catch((err) => console.log(err));

      console.log(result);
      if (result) {
        setNews(result);
      }
    }

    getNews();
  }, []);

  return (
    <Container className=" d-flex mt-3 mb-5">
      {news.map((e) => {
        if (isPromo(e)) {
          return <ShopNewsCard key={e.id} item={e} />;
        }
      })}
    </Container>
  );
};

export default Store;
