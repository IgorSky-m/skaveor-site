import { GATEWAY_API_ENTRANCE } from "./Constraints";
export default class MsgApi {
  constructor() {
    this.apiEndpoint = GATEWAY_API_ENTRANCE;
    this.emailPart = "messages/email";
  }

  send(subject, text, receiver, files, headers = {}) {
    const data = new FormData();
    data.append("subject", subject);
    data.append("text", text);
    data.append("receiver", receiver);
    if (files) {
      for (let index = 0; index < files.length; index++) {
        console.log(files[index].name);
        data.append("files", files[index], files[index].name);
      }
    }

    return fetch(`${this.apiEndpoint}/${this.emailPart}/send`, {
      method: "POST",

      body: data,
    });
  }
}
