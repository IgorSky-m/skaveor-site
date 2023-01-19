import { useNavigate } from "react-router-dom";
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
    );
  }

  getCategoriesPage(criteria) {
    return fetch(
      `${this.shopApiEndpoint}/${this.categoriesPart}/page?${
        this.criteriaParamName
      }=${encodeURIComponent(criteria ? criteria : "{}")}`,
      {
        method: "GET",
      }
    );
  }

  getCategory(categoryId) {
    return fetch(
      `${this.shopApiEndpoint}/${this.categoriesPart}/${categoryId}`
    );
  }

  getCategoryItems(categoryId) {
    return fetch(
      `${this.shopApiEndpoint}/${this.categoriesPart}/${categoryId}/${this.itemsPart}`
    );
  }

  getCategoryItemsPage(categoryId) {
    return fetch(
      `${this.shopApiEndpoint}/${this.categoriesPart}/${categoryId}/${this.itemsPart}/page`
    );
  }

  getItems(criteria) {
    return fetch(
      `${this.shopApiEndpoint}/${this.itemsPart}?${
        this.criteriaParamName
      }=${encodeURIComponent(criteria ? criteria : "{}")}`,
      {
        method: "GET",
      }
    );
  }

  getItemsPage(criteria) {
    return fetch(
      `${this.shopApiEndpoint}/${this.itemsPart}/page?${
        this.criteriaParamName
      }=${encodeURIComponent(criteria ? criteria : "{}")}`,
      {
        method: "GET",
      }
    );
  }

  getItem(itemId) {
    return fetch(`${this.shopApiEndpoint}/${this.itemsPart}/${itemId}`);
  }

  getDealTypes() {
    return fetch(`${this.shopApiEndpoint}/${this.dealsPart}/types`);
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
    );
  }

  searchItems(queryString) {
    return fetch(
      `${this.shopApiEndpoint}/${this.itemsPart}/search?q=${queryString}`,
      {
        method: "GET",
      }
    );
  }
}
