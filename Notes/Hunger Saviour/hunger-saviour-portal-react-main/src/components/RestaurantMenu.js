// RestaurantMenu.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import StarsIcon from '@mui/icons-material/Stars';
import { useAppContext } from '../AppContext';

function RestaurantMenu() {
  const { restaurantId } = useParams();
  const [expandedDescriptions, setExpandedDescriptions] = useState([]);
  const [menuItems, setMenuItems] = useState([]);
  const { state, setState } = useAppContext();

  useEffect(() => {
    axios.get(`http://localhost:8765/restaurants/${restaurantId}`)
      .then(res => {
        const menuItemsData = res.data.menuItems || [];
        setMenuItems(menuItemsData);
        setExpandedDescriptions(Array(menuItemsData.length).fill(false));
      })
      .catch(error => {
        console.error('Error fetching restaurants:', error);
      });
  }, [restaurantId]);

  const toggleDescription = (index) => {
    setExpandedDescriptions(prevState => {
      const newState = [...prevState];
      newState[index] = !newState[index];
      return newState;
    });
  };

  const addToCart = (menuItem) => {
    // Clear the cart if the restaurant ID of the new item is different
    if (state.cart.length > 0 && state.cart[0].restaurantId !== restaurantId) {
      setState(prevState => ({
        ...prevState,
        cart: []
      }));
    }

    const existingItemIndex = state.cart.findIndex(item => item.menuItem === menuItem.menuItem);
  
    if (existingItemIndex !== -1) {
      // If the item already exists in the cart, update its quantity
      const updatedCart = [...state.cart];
      updatedCart[existingItemIndex].quantity++;
      setState(prevState => ({
        ...prevState,
        cart: updatedCart
      }));
    } else {
      // If the item does not exist in the cart, add it with quantity 1
      setState(prevState => ({
        ...prevState,
        cart: [...prevState.cart, { ...menuItem, quantity: 1, restaurantId }]
      }));
    }
  };

  const increaseQuantity = (menuItem) => {
    setState(prevState => ({
      ...prevState,
      cart: prevState.cart.map(cartItem =>
        cartItem.menuItem === menuItem.menuItem
          ? { ...cartItem, quantity: cartItem.quantity + 1 }
          : cartItem
      )
    }));
  };

  const decreaseQuantity = (menuItem) => {
    const existingItem = state.cart.find(item => item.menuItem === menuItem.menuItem);
    if (existingItem && existingItem.quantity === 1) {
      // If quantity is 1, remove the item from the cart
      const updatedCart = state.cart.filter(item => item.menuItem !== menuItem.menuItem);
      setState(prevState => ({
        ...prevState,
        cart: updatedCart
      }));
    } else {
      setState(prevState => ({
        ...prevState,
        cart: prevState.cart.map(cartItem =>
          cartItem.menuItem === menuItem.menuItem && cartItem.quantity > 1
            ? { ...cartItem, quantity: cartItem.quantity - 1 }
            : cartItem
        )
      }));
    }
  };

  return (
    <div className='mt-5 pt-3'>
      {/* Render restaurant menus */}
      {menuItems &&
        menuItems.map((menuItem, index) => (
          <div key={menuItem.restaurantMenuId} className="container d-flex flex-wrap align-items-center justify-content-center my-2">
            <div className="col-lg-7">
              <div className="d-flex flex-column">
                <div className="fw-bolder">{menuItem.menuItem}</div>
                <div className="fw-bolder">{menuItem.price}</div>
                <div className="d-flex flex-row fw-bold">
                  <span style={{ color: '#60b246' }}><StarsIcon /></span>
                  4.2
                </div>
                <div className="text-secondary custom-scroll m-1">
                  {expandedDescriptions[index] ? menuItem.description : menuItem.description.slice(0, 150)}
                  {menuItem.description.length > 100 && (
                    <span>
                      <a href="#!" onClick={() => toggleDescription(index)} className="show fw-bold" style={{ color: '#FC8019' }}>
                        {expandedDescriptions[index] ? "Show less" : "Show more"}
                      </a>
                    </span>
                  )}
                </div>
                <div className="text-secondary fw-bold">{menuItem.menuType}</div>
              </div>
            </div>

            <div className="col-lg-3">
              <div className="card-image-container position-relative">
                <img src={menuItem.menuImageUrl} alt={menuItem.menuImageUrl} className="menu-image" />
              </div>
            </div>

            <div className="col-lg-2">
              <div className="restaurant-card no-shadow">
                <div className="card-image-container d-flex flex-wrap align-items-center justify-content-center">
                  {/* Show item quantity with buttons */}
                  {state.cart.some(item => item.menuItem === menuItem.menuItem) ? (
                    <div className="quantity">
                      <button className="btn btn-success" type="button" style={{ backgroundColor: '#FC8019', borderColor: '#FC8019' }} onClick={() => decreaseQuantity(menuItem)}><span className='fw-bold'>-</span></button>
                      <span className='p-2 fw-bolder'>{state.cart.find(item => item.menuItem === menuItem.menuItem).quantity}</span>
                      <button className="btn btn-success" type="button" style={{ backgroundColor: '#FC8019', borderColor: '#FC8019' }} onClick={() => increaseQuantity(menuItem)}><span className='fw-bold'>+</span></button>
                    </div>
                  ) : (
                    // Show "Add to Cart" button if item is not in the cart
                    <button className="btn btn-success" type="button" style={{ backgroundColor: '#FC8019', borderColor: '#FC8019' }} onClick={() => addToCart(menuItem)}>
                      Add To Cart
                    </button>
                  )}
                </div>
              </div>
            </div>
          </div>
        ))}
    </div>
  );
}

export default RestaurantMenu;
