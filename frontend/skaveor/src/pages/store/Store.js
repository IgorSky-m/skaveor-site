import React from "react";
import Button from "../../components/Button/Button";
import YoutubeEmbed from "../../components/Video/Youtube/YoutubeEmbed";

const Shop = () => {
  return (
    <>
      <div className="video-block">
        <div className="video-block-content">
          <YoutubeEmbed embedId="4SaF2NBNb0Q" />
        </div>
        <div className="video-block-text">
          <h3 className="text-title">Welcome to The</h3>
          <h1 className="game-name-title">
            <span className="game-name-words">Skaveor</span> game
          </h1>
          <h3 className="text-description">Lets enjoy & fun</h3>
          <Button path="/game" buttonText="Start Game" />
        </div>
      </div>
      <div className="shop-preview-block"></div>
    </>
  );
};

export default Shop;
