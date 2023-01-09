import React from "react";

import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import Home from "./pages/Home";
import SharedLayout from "./components/SharedLayout/SharedLayout";
import About from "./pages/About";
import News from "./pages/News";
import Login from "./pages/Login";
import Game from "./pages/Game";
import Error from "./pages/Error";
import Shop from "./pages/shop/Shop";
import ShopSharedLayout from "./components/Shop/ShopSharedLayout/ShopSharedLayout";
import Signup from "./pages/Signup";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<SharedLayout />}>
          <Route index element={<Home />} />
          <Route path="about" element={<About />} />
          <Route path="news" element={<News />} />
          <Route path="login" element={<Login />} />
          <Route path="signup" element={<Signup />} />
          <Route path="game" element={<Game />} />
          <Route path="store" element={<ShopSharedLayout />}>
            {/* Shop pages */}
          </Route>
        </Route>

        <Route to="*" element={<Error />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
