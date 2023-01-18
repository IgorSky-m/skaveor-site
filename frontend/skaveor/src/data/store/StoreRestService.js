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

  getCategories(criteria) {
    return fetch(
      `${this.shopApiEndpoint}/${this.categoriesPart}?${
        this.criteriaParamName
      }=${encodeURIComponent(criteria ? criteria : "{}")}`,
      {
        method: "GET",
      }
    )
      .then((response) => response.json())
      .catch((error) => console.error(error));
  }

  getCategoriesPage(criteria) {
    return fetch(
      `${this.shopApiEndpoint}/${this.categoriesPart}/page?${
        this.criteriaParamName
      }=${encodeURIComponent(criteria ? criteria : "{}")}`,
      {
        method: "GET",
      }
    )
      .then((response) => response.json())
      .catch((error) => console.error(error));
  }

  getCategory(categoryId) {
    return fetch(`${this.shopApiEndpoint}/${this.categoriesPart}/${categoryId}`)
      .then((response) => response.json())
      .catch((error) => console.error(error));
  }

  getCategoryItems(categoryId) {
    return fetch(
      `${this.shopApiEndpoint}/${this.categoriesPart}/${categoryId}/${this.itemsPart}`
    )
      .then((response) => response.json())
      .catch((error) => console.error(error));
  }

  getCategoryItemsPage(categoryId) {
    return fetch(
      `${this.shopApiEndpoint}/${this.categoriesPart}/${categoryId}/${this.itemsPart}/page`
    )
      .then((response) => response.json())
      .catch((error) => console.error(error));
  }

  getItems(criteria) {
    return fetch(
      `${this.shopApiEndpoint}/${this.itemsPart}?${
        this.criteriaParamName
      }=${encodeURIComponent(criteria ? criteria : "{}")}`,
      {
        method: "GET",
      }
    )
      .then((response) => response.json())
      .catch((error) => console.error(error));
  }

  getItemsPage(criteria) {
    return fetch(
      `${this.shopApiEndpoint}/${this.itemsPart}/page?${
        this.criteriaParamName
      }=${encodeURIComponent(criteria ? criteria : "{}")}`,
      {
        method: "GET",
      }
    )
      .then((response) => response.json())
      .catch((error) => console.error(error));
  }

  getItem(itemId) {
    return fetch(`${this.shopApiEndpoint}/${this.itemsPart}/${itemId}`)
      .then((response) => response.json())
      .catch((error) => console.error(error));
  }

  getDealTypes() {
    return fetch(`${this.shopApiEndpoint}/${this.dealsPart}/types`)
      .then((response) => response.json())
      .catch((error) => console.error(error));
  }

  getDealItems(dealType, criteria) {
    return fetch(
      `${this.shopApiEndpoint}/${this.dealsPart}/page?${
        this.criteriaParamName
      }=${encodeURIComponent(criteria ? criteria : "{}")}&${
        this.dealTypeParamName
      }=${dealType}`,
      {
        method: "GET",
      }
    )
      .then((response) => response.json())
      .catch((error) => console.error(error));
  }

  searchItems(queryString) {
    return fetch(
      `${this.shopApiEndpoint}/${this.itemsPart}/search?q=${queryString}`,
      {
        method: "GET",
      }
    )
      .then((response) => response.json())
      .catch((error) => console.error(error));
  }
}