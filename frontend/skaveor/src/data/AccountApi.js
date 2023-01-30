import { GATEWAY_API_ENTRANCE } from "./Constraints";
export default class AccountApi {
  constructor() {
    this.apiEndpoint = GATEWAY_API_ENTRANCE;
    this.userPart = "users";
  }

  getAllPage(headers = {}, pageable = {}) {
    const url = new URL(`${GATEWAY_API_ENTRANCE}/${this.userPart}/page`);
    const params = new URLSearchParams(pageable).toString();

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

  getOne(id, headers = {}) {
    return fetch(`${GATEWAY_API_ENTRANCE}/${this.userPart}/${id}`, {
      method: "GET",
      headers: {
        ...headers,
      },
    });
  }

  update(id, version, user, headers = {}) {
    return fetch(
      `${this.apiEndpoint}/${this.userPart}/${id}/dt_update/${version}`,
      {
        method: "PUT",
        headers: {
          ...headers,
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
      }
    );
  }

  activate(id, version, headers = {}) {
    return fetch(
      `${this.apiEndpoint}/${this.userPart}/${id}/dt_update/${version}/activate`,
      {
        method: "PUT",
        headers: {
          ...headers,
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      }
    );
  }

  deactivate(id, version, headers = {}) {
    return fetch(
      `${this.apiEndpoint}/${this.userPart}/${id}/dt_update/${version}/deactivate`,
      {
        method: "PUT",
        headers: {
          ...headers,
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      }
    );
  }
}
