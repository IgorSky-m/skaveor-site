import { createContext, useContext, useState } from "react";
import Cart from "../components/Cart/Cart";
import { useLocalStorage } from "../hooks/useLocalStorage";

const ShoppingCartContext = createContext({
  getItemQuantity: (id) => 0,
  increaseItemQuantity: (id) => {},
  decreaseItemQuantity: (id) => {},
  removeFromCart: (id) => {},
  setQuantity: (id, quantity) => {},
  openCart: () => {},
  closeCart: () => {},
  clearCart: () => {},
  cartQuantity: 0,
  cartItems: [],
});

export function useShoppingCart() {
  return useContext(ShoppingCartContext);
}

export function ShoppingCartProvider({ children }) {
  const [cartItems, setCartItems] = useLocalStorage("cart-items", []);
  const [isOpen, setIsOpen] = useState(false);
  const cartQuantity = cartItems.reduce(
    (quantity, e) => e.quantity + quantity,
    0
  );

  const openCart = () => setIsOpen(true);
  const closeCart = () => setIsOpen(false);
  function getItemQuantity(id) {
    const item = cartItems.find((e) => e.id === id);
    return item !== undefined ? item.quantity : 0;
  }
  function increaseItemQuantity(id) {
    console.log("increaseItemQuantity");
    setCartItems((currItems) => {
      if (currItems.find((e) => e.id === id) == null) {
        return [...cartItems, { id, quantity: 1 }];
      } else {
        return currItems.map((e) =>
          e.id === id ? { ...e, quantity: e.quantity + 1 } : e
        );
      }
    });
  }

  function decreaseItemQuantity(id) {
    console.log("decreaseItemQuantity");
    setCartItems((currItems) => {
      if (currItems.find((e) => e.id === id)?.quantity === 1) {
        return currItems.filter((e) => e.id !== id);
      } else {
        return currItems.map((e) =>
          e.id === id ? { ...e, quantity: e.quantity - 1 } : e
        );
      }
    });
  }

  function removeFromCart(id) {
    console.log("removeFromCart");
    setCartItems((currentItems) => currentItems.filter((e) => e.id !== id));
  }

  function setQuantity(id, quantity) {
    console.log("decreaseItemQuantity");
    setCartItems((currItems) => {
      if (quantity === 0) {
        removeFromCart(id);
      } else {
        if (currItems.find((e) => e.id === id) == null) {
          return [...cartItems, { id, quantity }];
        } else {
          return currItems.map((e) => (e.id === id ? { ...e, quantity } : e));
        }
      }
    });
  }

  function clearCart() {
    setCartItems([]);
  }

  return (
    <ShoppingCartContext.Provider
      value={{
        getItemQuantity,
        increaseItemQuantity,
        decreaseItemQuantity,
        removeFromCart,
        openCart,
        closeCart,
        clearCart,
        setQuantity,
        cartItems,
        cartQuantity,
      }}
    >
      {children}
      <Cart isOpen={isOpen} />
    </ShoppingCartContext.Provider>
  );
}
