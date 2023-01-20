import { GATEWAY_API_ENTRANCE } from "./Constraints";
export default class NewsApi {
  constructor() {
    this.shopApiEndpoint = GATEWAY_API_ENTRANCE;
    this.newsPart = "news";
    this.criteriaParamName = "criteria";
  }

  getNews(criteria, headers = {}) {
    return fetch(
      `${this.shopApiEndpoint}/${this.newsPart}?${
        this.criteriaParamName
      }=${encodeURIComponent(criteria ? criteria : "{}")}`,
      {
        method: "GET",
        headers: {
          ...headers,
        },
      }
    );
  }

  getNewsPage(criteria, headers = {}) {
    return fetch(
      `${this.shopApiEndpoint}/${this.newsPart}/page?${
        this.criteriaParamName
      }=${encodeURIComponent(criteria ? criteria : "{}")}`,
      {
        method: "GET",
        headers: {
          ...headers,
        },
      }
    );
  }
}
