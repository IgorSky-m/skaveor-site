import React, { useRef, useState } from "react";
import { Button, Container, Form, FormGroup } from "react-bootstrap";
import MsgApi from "../data/MsgApi";
const About = () => {
  const [about, setAbout] = useState({
    subject: "Suggestions",
    text: "",
    receiver: "",
  });

  const [files, setFiles] = useState(null);

  const filePicker = useRef();

  const [receiverSwitch, setReceiverSwitch] = useState(false);

  const [receiverClasses, setReceiverClasses] = useState("");
  const [receiverErrors, setReceiverErrors] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();
    const api = new MsgApi();

    api.send(about.subject, about.text, about.receiver, files);
  };

  const handlePick = () => {
    filePicker.current.click();
  };

  const isEmail = (email) =>
    /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(email);

  const validateEmail = () => {
    let msg;
    let receiverClasses;
    if (!receiverSwitch) {
      msg = "";
      receiverClasses = "";
    } else if (!about.receiver) {
      msg = "email empty";
      receiverClasses = "invalid-input";
    } else if (!isEmail(about.receiver)) {
      msg = "invalid email";
      receiverClasses = "invalid-input";
    } else {
      msg = "";
      receiverClasses = "";
    }
    setReceiverErrors((prev) => (prev === msg ? prev : msg));

    setReceiverClasses((prev) =>
      prev === receiverClasses ? prev : receiverClasses
    );
  };

  const flipReceiverSwitch = () => {
    setReceiverSwitch((prev) => !prev);
  };

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setAbout((prev) => {
      return { ...prev, [name]: value };
    });
  };

  return (
    <Container className="mt-3 text-center text-white text-shadow-cls">
      <h1>
        <span style={{ fontFamily: "Glitch" }}>Igar Skachko</span>
      </h1>
      <h3> Fullstack developer</h3>
      <a
        className="text-white fs-4 "
        href="https://www.linkedin.com/in/igar-skachko/"
      >
        LinkedIn
      </a>
      <Form className="mt-3 p-3 bg-dark rounded-0" onSubmit={handleSubmit}>
        <Form.Group controlId="subject" className="mb-4">
          <Form.Label className="text-white">Subject</Form.Label>
          <Form.Control
            name="subject"
            as="select"
            className="bg-dark rounded-0 text-white"
            value={about.subject}
            onChange={handleChange}
          >
            <option value="Suggestions">Suggestions</option>
            <option value="Bugs">Bugs</option>
            <option value="Other">Other</option>
          </Form.Control>
        </Form.Group>
        <Form.Group controlId="exampleForm.ControlTextarea1">
          <Form.Label>Text</Form.Label>
          <Form.Control
            className="mb-3 bg-dark rounded-0 text-white"
            as="textarea"
            style={{ resize: "none" }}
            rows="3"
            name="text"
            onChange={handleChange}
          />
        </Form.Group>
        <Form.Label>Reply to</Form.Label>
        <Form.Group className="d-flex justify-content-between align-items-center gap-5 mb-3">
          <div
            className="d-flex justify-content-start gap-5"
            style={{ width: "20%" }}
          >
            <Form.Label>Need reply?</Form.Label>
            <Form.Switch
              onChange={flipReceiverSwitch}
              type="switch"
              className="mb-3 bg-dark rounded-0 text-white"
              id="custom-switch"
              checked={receiverSwitch}
            />
          </div>
          <Form.Group
            className=" d-flex justify-content-between"
            style={{ width: "80%" }}
          >
            <Form.Control
              onBlur={validateEmail}
              type="email"
              disabled={!receiverSwitch}
              placeholder="Email address"
              autoFocus
              autoComplete="off"
              name="receiver"
              value={about.receiver}
              className={`mb-3 bg-dark rounded-0 text-white ${receiverClasses}`}
              onChange={handleChange}
              style={{ position: "relative" }}
            />
            {/* {receiverErrors && (
              <div className="position-absolute">{receiverErrors}</div>
            )} */}
          </Form.Group>
        </Form.Group>

        <Button
          style={{ backgroundColor: "rgba(0,0,0,0)" }}
          className="p-0 text-white border-0"
          onClick={handlePick}
        >
          <svg
            fill="white"
            height="50px"
            width="50px"
            version="1.1"
            id="Capa_1"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 490.955 490.955"
            stroke="#ffff"
          >
            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
            <g
              id="SVGRepo_tracerCarrier"
              stroke-linecap="round"
              stroke-linejoin="round"
            ></g>
            <g id="SVGRepo_iconCarrier">
              {" "}
              <path
                id="XMLID_448_"
                d="M445.767,308.42l-53.374-76.49v-20.656v-11.366V97.241c0-6.669-2.604-12.94-7.318-17.645L312.787,7.301 C308.073,2.588,301.796,0,295.149,0H77.597C54.161,0,35.103,19.066,35.103,42.494V425.68c0,23.427,19.059,42.494,42.494,42.494 h159.307h39.714c1.902,2.54,3.915,5,6.232,7.205c10.033,9.593,23.547,15.576,38.501,15.576c26.935,0-1.247,0,34.363,0 c14.936,0,28.483-5.982,38.517-15.576c11.693-11.159,17.348-25.825,17.348-40.29v-40.06c16.216-3.418,30.114-13.866,37.91-28.811 C459.151,347.704,457.731,325.554,445.767,308.42z M170.095,414.872H87.422V53.302h175.681v46.752 c0,16.655,13.547,30.209,30.209,30.209h46.76v66.377h-0.255v0.039c-17.685-0.415-35.529,7.285-46.934,23.46l-61.586,88.28 c-11.965,17.134-13.387,39.284-3.722,57.799c7.795,14.945,21.692,25.393,37.91,28.811v19.842h-10.29H170.095z M410.316,345.771 c-2.03,3.866-5.99,6.271-10.337,6.271h-0.016h-32.575v83.048c0,6.437-5.239,11.662-11.659,11.662h-0.017H321.35h-0.017 c-6.423,0-11.662-5.225-11.662-11.662v-83.048h-32.574h-0.016c-4.346,0-8.308-2.405-10.336-6.271 c-2.012-3.866-1.725-8.49,0.783-12.07l61.424-88.064c2.189-3.123,5.769-4.984,9.57-4.984h0.017c3.802,0,7.38,1.861,9.568,4.984 l61.427,88.064C412.04,337.28,412.328,341.905,410.316,345.771z"
              ></path>{" "}
            </g>
          </svg>
        </Button>
        <Form.Group>
          <Form.Control
            style={{ opacity: 0, height: 0, width: 0 }}
            ref={filePicker}
            accept="image/*,.png,.jpg,.gif,.web"
            multiple
            type="file"
            name="files"
            onChange={(e) => setFiles(e.target.files)}
          />
        </Form.Group>
        <Button type="submit" className="mt-3">
          Submit
        </Button>
      </Form>
      <div style={{ height: "100px" }}></div>
    </Container>
  );
};

export default About;
