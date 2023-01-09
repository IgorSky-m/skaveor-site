import React from "react";
import PropTypes from "prop-types";
import "./YoutubeEmbed.css";

const DEFAULT_WIDTH = "853";
const DEFAULT_HEIGTH = "480";
const DEFAULT_ALLOW =
  "accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture";
const DEFAULT_TITLE = "Youtube video";

const VIDEO_BASE_PATH = "https://www.youtube.com/embed";

const YoutubeEmbed = ({ className, title, allow, width, height, embedId }) => (
  <div className="video-responsive">
    <iframe
      // style="border:0;"
      className={className || "video-frame"}
      // width={width ? width : DEFAULT_WIDTH}
      // height={height ? height : DEFAULT_HEIGTH}
      src={`${VIDEO_BASE_PATH}/${embedId}`}
      allow={allow ? allow : DEFAULT_ALLOW}
      allowFullScreen
      title={title ? title : DEFAULT_TITLE}
    />
  </div>
);

YoutubeEmbed.propTypes = {
  embedId: PropTypes.string.isRequired,
};

export default YoutubeEmbed;
