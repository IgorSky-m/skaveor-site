import { GATEWAY_API_ENTRANCE } from "./Constraints";
export default class AuthApi {
  constructor() {
    this.apiEndpoint = GATEWAY_API_ENTRANCE;
    this.authPart = "auth";
  }

  getRoles(headers = {}) {
    return fetch(`${this.apiEndpoint}/${this.authPart}/roles`, {
      method: "GET",
      headers: {
        ...headers,
      },
    });
  }

  getPayload(headers = {}) {
    return fetch(`${this.apiEndpoint}/${this.authPart}/payload`, {
      method: "GET",
      headers: {
        ...headers,
      },
    });
  }

  validateToken(token, headers = {}) {
    return fetch(`${this.apiEndpoint}/${this.authPart}/validate`, {
      method: "GET",
      headers: {
        ...headers,
        Authorization: token,
        "Access-Control-Allow-Origin": "*",
      },
    });
  }

  login(request, headers = {}) {
    return fetch(`${this.apiEndpoint}/${this.authPart}/login`, {
      method: "POST",
      headers: {
        ...headers,
        Accept: "application/json",
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      body: JSON.stringify(request),
    });
  }

  register(request, headers = {}) {
    return fetch(`${this.apiEndpoint}/${this.authPart}/register`, {
      method: "POST",
      headers: {
        ...headers,
        Accept: "application/json",
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      body: JSON.stringify(request),
    });
  }
}
