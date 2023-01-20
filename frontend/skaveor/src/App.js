import React from "react";

import { BrowserRouter, Route, Routes } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import Home from "./pages/Home";
import SharedLayout from "./components/SharedLayout/SharedLayout";
import About from "./pages/About";
import News from "./pages/News";
import Game from "./pages/Game";
import Error from "./pages/Error";
import Store from "./pages/store/Store";
import StoreSharedLayout from "./components/Shop/StoreSharedLayout/StoreSharedLayout";
import Signup from "./components/Login/Signup";
import CategoriesSharedLayout from "./components/Shop/Categories/CategoriesSharedLayout";
import StoreCategories from "./pages/store/categories/StoreCategories";
import StoreCategory from "./pages/store/categories/StoreCategory";
import Item from "./pages/store/items/Item";
import Deals from "./pages/store/deals/Deals";
import Deal from "./pages/store/deals/Deal";
import Checkout from "./pages/store/Checkout";
import OrderDetails from "./pages/store/OrderDetails";
import SearchResult from "./pages/SearchResult";
import { LoginProvider } from "./context/LoginContext";
function App() {
  return (
    <>
      <BrowserRouter>
        <LoginProvider>
          <Routes>
            <Route path="/" element={<SharedLayout />}>
              <Route index element={<Home />} />
              <Route path="home" element={<Home />} />
              <Route path="about" element={<About />} />
              <Route path="news" element={<News />} />
              <Route path="game" element={<Game />} />
            </Route>
            <Route path="store" element={<StoreSharedLayout />}>
              <Route index element={<Store />} />
              <Route path="search" element={<SearchResult />} />
              <Route path="categories" element={<CategoriesSharedLayout />}>
                <Route index element={<StoreCategories />} />
                <Route path=":categoryId" element={<StoreCategory />} />
              </Route>
              <Route path="items">
                <Route path=":itemId" element={<Item />} />
              </Route>
              <Route path="deals">
                <Route index element={<Deals />} />
                <Route path=":dealType" element={<Deal />} />
              </Route>
              <Route path="checkout" element={<Checkout />} />
              <Route path="order/:orderId" element={<OrderDetails />} />
            </Route>

            <Route to="*" element={<Error />} />
          </Routes>
        </LoginProvider>
      </BrowserRouter>
    </>
  );
}

export default App;
