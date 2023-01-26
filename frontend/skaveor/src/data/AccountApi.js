import { GATEWAY_API_ENTRANCE } from "./Constraints";
export default class AccountApi {
  constructor() {
    this.apiEndpoint = GATEWAY_API_ENTRANCE;
    this.userPart = "users";
  }

  getAllPage(headers = {}, pageable = {}) {
    const url = new URL(`${GATEWAY_API_ENTRANCE}/${this.userPart}/page`);
    const params = new URLSearchParams(pageable).toString();
    console.log(params);
    url.search = params;

    return fetch(url, {
      method: "GET",
      headers: {
        ...headers,
      },
    });
  }

  getAll(headers = {}, sortParams = {}) {
    const url = new URL(`${GATEWAY_API_ENTRANCE}/${this.userPart}`);
    const params = new URLSearchParams(sortParams).toString;
    url.search = params;

    return fetch(url, {
      method: "GET",
      headers: {
        ...headers,
      },
    });
  }
}
