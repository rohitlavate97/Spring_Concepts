import React, { useState } from 'react';
import './App.css';
import { Routes, Route } from "react-router-dom";
import Login from './components/Login';
import Restaurants from './components/Restaurants';
import Header from './components/Header';
import RestaurantMenu from './components/RestaurantMenu';
import Cart from './components/Cart';
import Orders from './components/Orders';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleLogin = () => {
    setIsLoggedIn(true);
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
  };

  return (
    <>
      <Header isLoggedIn={isLoggedIn} handleLogout={handleLogout} />
      <Routes>
        <Route path="/" element={<Login handleLogin={handleLogin} />} />
        <Route path="/login" element={<Login handleLogin={handleLogin} />} />
        <Route path="/restaurants" element={<Restaurants />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/orders" element={<Orders />} />
        <Route path="/restaurants/:restaurantId" element={<RestaurantMenu />} /> {/* Include restaurantId as URL parameter */}
      </Routes>
    </>
  );
}

export default App;
