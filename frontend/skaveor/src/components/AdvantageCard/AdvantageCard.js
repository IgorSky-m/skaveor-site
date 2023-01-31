import React from "react";
import "./AdvantageCard.css";

const DEFAULT_ALT_TEXT = "game advantage description";
const NO_IMAGE_URL = "no-Image-Icon.png";

const AdvantageCard = ({ item: { img, alt, description } }) => {
  return (
    <div className="advantage-card">
      <img
        className="advantage-card-img"
        src={`/img/icons/${img ? img : NO_IMAGE_URL}`}
        alt={alt ? alt : DEFAULT_ALT_TEXT}
      />
      {description && (
        <div className="advantage-card-description">
          <h5 className="advantage-card-description-text">{description}</h5>
        </div>
      )}
    </div>
  );
};

export default AdvantageCard;
