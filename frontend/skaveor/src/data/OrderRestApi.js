import { GATEWAY_API_ENTRANCE } from "./Constraints";
export default class OrderApi {
  constructor() {
    this.apiEndpoint = GATEWAY_API_ENTRANCE;
    this.orderPart = "order";
  }

  placeOrderPromise(request) {
    return fetch(`${this.apiEndpoint}/${this.orderPart}/place`, {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(request),
    });
  }

  placeOrder(request) {
    return this.placeOrderPromise(request)
      .then((response) => response.json())
      .catch((error) => console.error(error));
  }

  getOrderDetails(id) {
    return fetch(`${this.apiEndpoint}/${this.orderPart}/${id}`, {
      method: "GET",
    })
      .then((response) => response.json())
      .catch((error) => console.error(error));
  }
}
