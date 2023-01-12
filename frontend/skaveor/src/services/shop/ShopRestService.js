export default class ShopApi {
  constructor() {
    this.shopApiEndpoint = "http://localhost:9000";
    this.itemsPart = "items";
    this.categoriesPart = "categories";
    this.criteriaParamName = "criteria";
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
}
