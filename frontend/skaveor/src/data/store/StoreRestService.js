import { GATEWAY_API_ENTRANCE } from "../Constraints";
export default class StoreApi {
  constructor() {
    this.shopApiEndpoint = GATEWAY_API_ENTRANCE;
    this.itemsPart = "items";
    this.dealsPart = "deals";
    this.categoriesPart = "categories";
    this.criteriaParamName = "criteria";
    this.dealTypeParamName = "type";
  }

  getCategories(criteria, headers = {}) {
    return fetch(
      `${this.shopApiEndpoint}/${this.categoriesPart}?${
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

  getCategoriesPage(criteria, headers = {}) {
    return fetch(
      `${this.shopApiEndpoint}/${this.categoriesPart}/page?${
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

  getCategory(categoryId, headers = {}) {
    return fetch(
      `${this.shopApiEndpoint}/${this.categoriesPart}/${categoryId}`,
      {
        method: "GET",
        headers: {
          ...headers,
        },
      }
    );
  }

  getCategoryItems(categoryId, headers = {}) {
    return fetch(
      `${this.shopApiEndpoint}/${this.categoriesPart}/${categoryId}/${this.itemsPart}`,
      {
        method: "GET",
        headers: {
          ...headers,
        },
      }
    );
  }

  getCategoryItemsPage(categoryId, headers = {}) {
    return fetch(
      `${this.shopApiEndpoint}/${this.categoriesPart}/${categoryId}/${this.itemsPart}/page`,
      {
        method: "GET",
        headers: {
          ...headers,
        },
      }
    );
  }

  getItems(criteria, headers = {}) {
    return fetch(
      `${this.shopApiEndpoint}/${this.itemsPart}?${
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

  getItemsPage(criteria, headers = {}) {
    return fetch(
      `${this.shopApiEndpoint}/${this.itemsPart}/page?${
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

  getItem(itemId, headers = {}) {
    return fetch(`${this.shopApiEndpoint}/${this.itemsPart}/${itemId}`, {
      method: "GET",
      headers: {
        ...headers,
      },
    });
  }

  getDealTypes(headers = {}) {
    return fetch(`${this.shopApiEndpoint}/${this.dealsPart}/types`, {
      method: "GET",
      headers: {
        ...headers,
      },
    });
  }

  getDealItems(dealType, criteria, headers = {}) {
    return fetch(
      `${this.shopApiEndpoint}/${this.dealsPart}/page?${
        this.criteriaParamName
      }=${encodeURIComponent(criteria ? criteria : "{}")}&${
        this.dealTypeParamName
      }=${dealType}`,
      {
        method: "GET",
        headers: {
          ...headers,
        },
      }
    );
  }

  searchItems(queryString, headers = {}) {
    return fetch(
      `${this.shopApiEndpoint}/${this.itemsPart}/search?q=${queryString}`,
      {
        method: "GET",
        headers: {
          ...headers,
        },
      }
    );
  }
}
