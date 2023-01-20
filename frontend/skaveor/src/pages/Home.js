import React from "react";

import AdvantageCard from "../components/AdvantageCard/AdvantageCard";
import advantages from "../mock/AdvantagesData";
import { Button, Container, Nav } from "react-bootstrap";
import VideoCarousel from "../components/VideoCarousel";
import { useNavigate } from "react-router-dom";

const Home = ({ user }) => {
  const navigate = useNavigate();
  return (
    <Container className=" text-center align-items-center mt-3 d-flex justify-content-between flex-column">
      <VideoCarousel
        width="60%"
        height="300px"
        links={[
          {
            src: "/video/promo/space.mp4",
            label: "Hurry",
            description: "Skaveor needs to your protection",
          },
          {
            src: "/video/promo/marines-2.mp4",
            label: "Biobots are Coming",
            description: "Protect your land and fight agaist evil",
          },
        ]}
      />

      <div className="advantages-block" style={{ fontFamily: "Skygraze" }}>
        {advantages.map((e) => {
          console.log(e);
          return <AdvantageCard key={e.id} item={e} />;
        })}
      </div>

      <Button
        variant="success"
        className="p-2 rounded-0 text-uppercase"
        style={{ width: "60%", fontFamily: "GameCube" }}
        onClick={() => navigate("/game")}
      >
        Start game
      </Button>
    </Container>
  );
};

export default Home;
