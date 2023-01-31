import React, { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import NewsCard from "../components/News/NewsCard";
import NewsApi from "../data/NewsApi";

export const News = () => {
  const [news, setNews] = useState([]);
  useEffect(() => {
    const api = new NewsApi();

    async function getNews() {
      const result = await api
        .getNews()
        .then((resp) => resp.json())
        .catch((err) => console.log(err));

      if (result) {
        setNews(result);
      }
    }

    getNews();
  }, []);

  return (
    <Container className=" d-flex flex-column mt-3 mb-5">
      {news.map((e) => (
        <NewsCard key={e.id} item={e} />
      ))}
    </Container>
  );
};

export default News;
