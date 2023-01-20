import { GATEWAY_API_ENTRANCE } from "./Constraints";
export default class OrderApi {
  constructor() {
    this.apiEndpoint = GATEWAY_API_ENTRANCE;
    this.orderPart = "order";
  }

  placeOrder(request, headers = {}) {
    return fetch(`${this.apiEndpoint}/${this.orderPart}/place`, {
      method: "POST",
      headers: {
        ...headers,
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(request),
    });
  }

  getOrderDetails(id, headers = {}) {
    return fetch(`${this.apiEndpoint}/${this.orderPart}/${id}`, {
      method: "GET",
      headers: {
        ...headers,
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    });
  }
}
