import { GATEWAY_API_ENTRANCE } from "./Constraints";
export default class AuthApi {
  constructor() {
    this.apiEndpoint = GATEWAY_API_ENTRANCE;
    this.authPart = "auth";
  }

  validateToken(token) {
    return fetch(`${this.apiEndpoint}/${this.authPart}/validate`, {
      method: "GET",
      headers: {
        Authorization: token,
        "Access-Control-Allow-Origin": "*",
      },
    });
  }

  login(request) {
    return fetch(`${this.apiEndpoint}/${this.authPart}/login`, {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      body: JSON.stringify(request),
    });
  }

  register(request) {
    return fetch(`${this.apiEndpoint}/${this.authPart}/register`, {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      body: JSON.stringify(request),
    });
  }
}
