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
import Store from "./pages/store/Store";
import StoreSharedLayout from "./components/Shop/StoreSharedLayout/StoreSharedLayout";
import Signup from "./pages/Signup";
import CategoriesSharedLayout from "./components/Shop/Categories/CategoriesSharedLayout";
import StoreCategories from "./pages/store/categories/StoreCategories";
import StoreCategory from "./pages/store/categories/StoreCategory";
import Item from "./pages/store/items/Item";
import Deals from "./pages/store/deals/Deals";
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

          <Route path="store" element={<StoreSharedLayout />}>
            <Route index element={<Store />} />
            <Route path="categories" element={<CategoriesSharedLayout />}>
              <Route index element={<StoreCategories />} />
              <Route path=":categoryId" element={<StoreCategory />} />
            </Route>
            <Route path="items">
              <Route path=":itemId" element={<Item />} />
            </Route>
            <Route path="deals" element={<Deals />} />
          </Route>
        </Route>

        <Route to="*" element={<Error />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
