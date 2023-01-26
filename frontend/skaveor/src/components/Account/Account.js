import React, { useEffect, useState } from "react";
import {
  Button,
  Container,
  Image,
  NavLink,
  Offcanvas,
  OverlayTrigger,
  Stack,
  Tooltip,
} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useAccount } from "../../context/AccountContext";
import { useLogin } from "../../context/LoginContext";

const Account = ({ isOpen }) => {
  const { closeAccount, payload, isAdmin } = useAccount();
  const { logOut } = useLogin();
  const navigate = useNavigate();

  return (
    <Offcanvas
      show={isOpen}
      scroll={true}
      placement="end"
      onHide={closeAccount}
      className="bg-dark text-light"
    >
      <Offcanvas.Header>
        <OverlayTrigger
          placement="bottom"
          overlay={<Tooltip id="button-tooltip-2">Log out</Tooltip>}
        >
          {({ ref, ...triggerHandler }) => (
            <Button
              variant="outline-secondary"
              style={{ position: "absolute", top: "10px", right: "70px" }}
              onClick={() => {
                logOut();
                navigate("/");
                closeAccount();
              }}
              className="rounded-0 border-0"
            >
              <svg
                width="25px"
                viewBox="0 0 15 15"
                fill="currentColor"
                xmlns="http://www.w3.org/2000/svg"
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
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M3 1C2.44771 1 2 1.44772 2 2V13C2 13.5523 2.44772 14 3 14H10.5C10.7761 14 11 13.7761 11 13.5C11 13.2239 10.7761 13 10.5 13H3V2L10.5 2C10.7761 2 11 1.77614 11 1.5C11 1.22386 10.7761 1 10.5 1H3ZM12.6036 4.89645C12.4083 4.70118 12.0917 4.70118 11.8964 4.89645C11.7012 5.09171 11.7012 5.40829 11.8964 5.60355L13.2929 7H6.5C6.22386 7 6 7.22386 6 7.5C6 7.77614 6.22386 8 6.5 8H13.2929L11.8964 9.39645C11.7012 9.59171 11.7012 9.90829 11.8964 10.1036C12.0917 10.2988 12.4083 10.2988 12.6036 10.1036L14.8536 7.85355C15.0488 7.65829 15.0488 7.34171 14.8536 7.14645L12.6036 4.89645Z"
                    fill="currentColor"
                  ></path>{" "}
                </g>
              </svg>
            </Button>
          )}
        </OverlayTrigger>

        <Button
          variant="outline-secondary"
          style={{ position: "absolute", top: "10px", right: "15px" }}
          onClick={() => {
            closeAccount();
          }}
          className="rounded-0 border-0"
        >
          <svg
            style={{ width: "1.5rem", height: "1.5rem" }}
            width="20px"
            height="20px"
            viewBox="50 50 400 400"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            fill="#0000"
            stroke="#0000"
          >
            <g id="SVGRepo_bgCarrier" strokeWidth="0"></g>
            <g
              id="SVGRepo_tracerCarrier"
              strokeLinecap="round"
              strokeLinejoin="round"
            ></g>
            <g id="SVGRepo_iconCarrier">
              {" "}
              <title>close</title>{" "}
              <g
                id="Page-1"
                stroke="none"
                strokeWidth="1"
                fill="currentColor"
                fill-rule="evenodd"
              >
                {" "}
                <g
                  id="Combined-Shape"
                  fill="currentColor"
                  transform="translate(91.581722, 91.581722)"
                >
                  {" "}
                  <path
                    d="M298.666667,4.26325641e-14 L328.836556,30.1698893 L194.587,164.418 L328.836556,298.666667 L298.666667,328.836556 L164.418,194.587 L30.1698893,328.836556 L9.9475983e-14,298.666667 L134.248,164.418 L7.10542736e-14,30.1698893 L30.1698893,4.26325641e-14 L164.418,134.248 L298.666667,4.26325641e-14 Z"
                    transform="translate(164.418278, 164.418278) rotate(-360.000000) translate(-164.418278, -164.418278) "
                  >
                    {" "}
                  </path>{" "}
                </g>{" "}
              </g>{" "}
            </g>
          </svg>
        </Button>

        <Offcanvas.Title>
          <span className="fs-5">Welcome back</span>{" "}
          <span className="fs-5 text-capitalize">{payload.name}</span>
        </Offcanvas.Title>
      </Offcanvas.Header>
      <Offcanvas.Body style={{ position: "relative" }}>
        <Stack className="gap-1">
          {isAdmin && (
            <Button
              variant="outline-secondary"
              className="d-flex align-items-center gap-2 text-start text-white rounded-0 border-0"
              onClick={() => {
                navigate("/admin");
                closeAccount();
              }}
            >
              <div className="  d-flex">
                <svg
                  fill="#FFFF"
                  height="20px"
                  width="20px"
                  version="1.1"
                  id="Capa_1"
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 474.565 474.565"
                  stroke="#FFFF"
                >
                  <g id="SVGRepo_bgCarrier" strokeWidth="0"></g>
                  <g
                    id="SVGRepo_tracerCarrier"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                  ></g>
                  <g id="SVGRepo_iconCarrier">
                    {" "}
                    <g>
                      {" "}
                      <path d="M255.204,102.3c-0.606-11.321-12.176-9.395-23.465-9.395C240.078,95.126,247.967,98.216,255.204,102.3z"></path>{" "}
                      <path d="M134.524,73.928c-43.825,0-63.997,55.471-28.963,83.37c11.943-31.89,35.718-54.788,66.886-63.826 C163.921,81.685,150.146,73.928,134.524,73.928z"></path>{" "}
                      <path d="M43.987,148.617c1.786,5.731,4.1,11.229,6.849,16.438L36.44,179.459c-3.866,3.866-3.866,10.141,0,14.015l25.375,25.383 c1.848,1.848,4.38,2.888,7.019,2.888c2.61,0,5.125-1.04,7.005-2.888l14.38-14.404c2.158,1.142,4.55,1.842,6.785,2.827 c0-0.164-0.016-0.334-0.016-0.498c0-11.771,1.352-22.875,3.759-33.302c-17.362-11.174-28.947-30.57-28.947-52.715 c0-34.592,28.139-62.739,62.723-62.739c23.418,0,43.637,13.037,54.43,32.084c11.523-1.429,22.347-1.429,35.376,1.033 c-1.676-5.07-3.648-10.032-6.118-14.683l14.396-14.411c1.878-1.856,2.918-4.38,2.918-7.004c0-2.625-1.04-5.148-2.918-7.004 l-25.361-25.367c-1.94-1.941-4.472-2.904-7.003-2.904c-2.532,0-5.063,0.963-6.989,2.904l-14.442,14.411 c-5.217-2.764-10.699-5.078-16.444-6.825V9.9c0-5.466-4.411-9.9-9.893-9.9h-35.888c-5.451,0-9.909,4.434-9.909,9.9v20.359 c-5.73,1.747-11.213,4.061-16.446,6.825L75.839,22.689c-1.942-1.941-4.473-2.904-7.005-2.904c-2.531,0-5.077,0.963-7.003,2.896 L36.44,48.048c-1.848,1.864-2.888,4.379-2.888,7.012c0,2.632,1.04,5.148,2.888,7.004l14.396,14.403 c-2.75,5.218-5.063,10.708-6.817,16.438H23.675c-5.482,0-9.909,4.441-9.909,9.915v35.889c0,5.458,4.427,9.908,9.909,9.908H43.987z"></path>{" "}
                      <path d="M354.871,340.654c15.872-8.705,26.773-25.367,26.773-44.703c0-28.217-22.967-51.168-51.184-51.168 c-9.923,0-19.118,2.966-26.975,7.873c-4.705,18.728-12.113,36.642-21.803,52.202C309.152,310.022,334.357,322.531,354.871,340.654z "></path>{" "}
                      <path d="M460.782,276.588c0-5.909-4.799-10.693-10.685-10.693H428.14c-1.896-6.189-4.411-12.121-7.393-17.75l15.544-15.544 c2.02-2.004,3.137-4.721,3.137-7.555c0-2.835-1.118-5.553-3.137-7.563l-27.363-27.371c-2.08-2.09-4.829-3.138-7.561-3.138 c-2.734,0-5.467,1.048-7.547,3.138l-15.576,15.552c-5.623-2.982-11.539-5.481-17.751-7.369v-21.958 c0-5.901-4.768-10.685-10.669-10.685H311.11c-2.594,0-4.877,1.04-6.739,2.578c3.26,11.895,5.046,24.793,5.046,38.552 c0,8.735-0.682,17.604-1.956,26.423c7.205-2.656,14.876-4.324,22.999-4.324c36.99,0,67.086,30.089,67.086,67.07 c0,23.637-12.345,44.353-30.872,56.303c13.48,14.784,24.195,32.324,31.168,51.976c1.148,0.396,2.344,0.684,3.54,0.684 c2.733,0,5.467-1.04,7.563-3.13l27.379-27.371c2.004-2.004,3.106-4.721,3.106-7.555s-1.102-5.551-3.106-7.563l-15.576-15.552 c2.982-5.621,5.497-11.555,7.393-17.75h21.957c2.826,0,5.575-1.118,7.563-3.138c2.004-1.996,3.138-4.72,3.138-7.555 L460.782,276.588z"></path>{" "}
                      <path d="M376.038,413.906c-16.602-48.848-60.471-82.445-111.113-87.018c-16.958,17.958-37.954,29.351-61.731,29.351 c-23.759,0-44.771-11.392-61.713-29.351c-50.672,4.573-94.543,38.17-111.145,87.026l-9.177,27.013 c-2.625,7.773-1.368,16.338,3.416,23.007c4.783,6.671,12.486,10.631,20.685,10.631h315.853c8.215,0,15.918-3.96,20.702-10.631 c4.767-6.669,6.041-15.234,3.4-23.007L376.038,413.906z"></path>{" "}
                      <path d="M120.842,206.782c0,60.589,36.883,125.603,82.352,125.603c45.487,0,82.368-65.014,82.368-125.603 C285.563,81.188,120.842,80.939,120.842,206.782z"></path>{" "}
                    </g>{" "}
                  </g>
                </svg>{" "}
              </div>
              <span>Admin panel</span>
            </Button>
          )}
          <Button
            variant="outline-secondary"
            className="d-flex align-items-center gap-2 text-start text-white rounded-0 border-0"
            onClick={() => navigate("/admin")}
          >
            <div className="  d-flex">
              <svg
                height="20px"
                width="20px"
                version="1.1"
                id="_x32_"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 512 512"
                fill="#FFFF"
                stroke="#FFFF"
              >
                <g id="SVGRepo_bgCarrier" strokeWidth="0"></g>
                <g
                  id="SVGRepo_tracerCarrier"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                ></g>
                <g id="SVGRepo_iconCarrier">
                  {" "}
                  <style type="text/css"> </style>{" "}
                  <g>
                    {" "}
                    <path
                      className="st0"
                      d="M497.569,215.169l-55.341-13.064c-4.037-13.955-9.495-27.277-16.376-39.735l29.844-48.304 c4.57-7.395,3.45-16.945-2.688-23.084l-31.992-31.991c-6.128-6.129-15.67-7.248-23.073-2.68l-48.286,29.854 c-12.468-6.881-25.789-12.348-39.734-16.376l-13.064-55.368c-2-8.44-9.551-14.422-18.23-14.422h-45.24 c-8.679,0-16.22,5.982-18.22,14.422L202.105,69.79c-13.955,4.028-27.276,9.478-39.735,16.376l-48.304-29.871 c-7.386-4.551-16.945-3.441-23.084,2.697L58.992,90.974c-6.129,6.137-7.248,15.706-2.689,23.082l29.863,48.313 c-6.881,12.458-12.349,25.78-16.367,39.716L14.422,215.15C5.982,217.169,0,224.701,0,233.389v45.231 c0,8.679,5.982,16.239,14.422,18.229L69.8,309.914c4.028,13.945,9.486,27.257,16.367,39.707l-29.872,48.322 c-4.551,7.394-3.441,16.954,2.697,23.101l31.982,31.973c6.138,6.147,15.706,7.256,23.082,2.688l48.322-29.872 c12.469,6.89,25.79,12.349,39.726,16.367l13.064,55.368c2,8.468,9.541,14.431,18.22,14.431h45.24c8.679,0,16.23-5.964,18.23-14.431 l13.064-55.368c13.936-4.018,27.258-9.477,39.708-16.367l48.313,29.863c7.403,4.57,16.945,3.45,23.092-2.688l31.982-31.992 c6.128-6.128,7.248-15.688,2.678-23.083l-29.863-48.304c6.899-12.45,12.349-25.771,16.377-39.716l55.368-13.065 c8.468-2,14.422-9.541,14.422-18.229V233.38C512,224.71,506.036,217.169,497.569,215.169z M255.995,397.319 c-78.001,0-141.296-63.295-141.296-141.324c0-78.038,63.296-141.324,141.296-141.324c78.03,0,141.324,63.286,141.324,141.324 C397.319,334.024,334.025,397.319,255.995,397.319z"
                    ></path>{" "}
                    <path
                      className="st0"
                      d="M256.005,180.507c-41.671,0-75.469,33.809-75.469,75.488c0,41.68,33.798,75.488,75.469,75.488 c41.68,0,75.487-33.808,75.487-75.488C331.492,214.316,297.684,180.507,256.005,180.507z"
                    ></path>{" "}
                  </g>{" "}
                </g>
              </svg>
            </div>
            <span>Settings</span>
          </Button>
        </Stack>
      </Offcanvas.Body>
    </Offcanvas>
  );
};

export default Account;
