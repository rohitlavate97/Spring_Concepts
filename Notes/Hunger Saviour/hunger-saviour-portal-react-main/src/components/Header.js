import React, { useState, useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';
import InfoIcon from '@mui/icons-material/Info';
import RestaurantIcon from '@mui/icons-material/Restaurant';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import LoginIcon from '@mui/icons-material/Login';
import LogoutIcon from '@mui/icons-material/Logout';
import { useAppContext } from '../AppContext';

function Header({ isLoggedIn, handleLogout }) {
  const location = useLocation();
  const [activeLink, setActiveLink] = useState('');
  const { state } = useAppContext();

  useEffect(() => {
    setActiveLink(location.pathname.split('/')[1]); // Get the first segment of the pathname
  }, [location]);

  // Calculate total count of items in the cart
  const totalItemCount = state.cart.reduce((total, item) => total + item.quantity, 0);

  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-light bg-light shadow fixed-top">
        <div className="container-fluid">
          <Link to="/" className="navbar-brand d-flex align-items-center fw-bold" style={{ textDecoration: 'none', color: '#FC8019' }}>
            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRPp1ohLNn2RGU2lTyfORSITnkv7U8Jci4oCa-5T1DfFc5JpagoQkZ6imLo-_vrE3aJw10&usqp=CAU" alt="" height="30" className="me-2" />
            Hunger Saviour
          </Link>
          <div className="navbar-nav ms-auto fw-bold gap-5">
            {!isLoggedIn && (
              <Link
                to="/login"
                className={"nav-link " + (activeLink === "login" ? "active" : "")}
                style={{ color: activeLink === "login" ? '#FC8019' : '#5C5B5B' }}
              >
                <LoginIcon /> Login
              </Link>
            )}
            {isLoggedIn && (
              <>
                <Link
                  to="/orders"
                  className={"nav-link " + (activeLink === "orders" ? "active" : "")}
                  style={{ color: activeLink === "orders" ? '#FC8019' : '#5C5B5B' }}
                >
                  <InfoIcon /> Orders
                </Link>
                <Link
                  to="/restaurants"
                  className={"nav-link " + (activeLink === "restaurants" || activeLink === "restaurant" ? "active" : "")} 
                  style={{ color: (activeLink === "restaurants" || activeLink === "restaurant") ? '#FC8019' : '#5C5B5B' }} 
                >
                  <RestaurantIcon /> Restaurants
                </Link>
                <Link
                  to="/cart"
                  className={"nav-link " + (activeLink === "cart" ? "active" : "")}
                  style={{ color: activeLink === "cart" ? '#FC8019' : '#5C5B5B' }}
                >
                  <ShoppingCartIcon />
                  Cart {totalItemCount > 0 && <span className="badge" style={{backgroundColor: '#FC8019'}}>{totalItemCount}</span>}
                </Link>
                <Link
                  to="/"
                  className={"nav-link " + (activeLink === "" ? "active" : "")}
                  style={{ color: activeLink === "" ? '#FC8019' : '#5C5B5B' }}
                  onClick={handleLogout}
                >
                  <LogoutIcon /> Logout
                </Link>
              </>
            )}
          </div>
        </div>
      </nav>
    </div>
  );
}

export default Header;
