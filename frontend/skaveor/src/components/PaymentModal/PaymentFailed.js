import React from "react";
import { Button, Modal } from "react-bootstrap";

export default function PaymentFailed({ show, onHide }) {
  return (
    <Modal
      show={show}
      onHide={onHide}
      size="sm"
      aria-labelledby="contained-modal-title-vcenter"
      centered
      className="shadow-lg"
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Payment Failed
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div className="d-flex justify-content-center">
          Oops, we can't recieve payment.
        </div>
        <div className="d-flex justify-content-center">Please try again.</div>
      </Modal.Body>
      <Modal.Footer>
        <Button className="btn-primary" onClick={onHide}>
          Try Again
        </Button>
      </Modal.Footer>
    </Modal>
  );
}
