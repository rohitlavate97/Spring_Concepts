import React from 'react';
import { useAppContext } from '../AppContext';
import '../index.css';
import { useUsernameContext } from '../UsernameContext';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Cart() {
  const { state, setState } = useAppContext();
  const { username } = useUsernameContext();
  const navigate = useNavigate();

  const clearCart = () => {
    // Reset the cart to an empty array
    setState(prevState => ({
      ...prevState,
      cart: []
    }));
  };

  const removeFromCart = (menuItem) => {
    const updatedCart = state.cart.filter(item => item.menuItem !== menuItem.menuItem);
    setState(prevState => ({
      ...prevState,
      cart: updatedCart
    }));
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
      removeFromCart(menuItem);
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

  const calculateItemPrice = (item) => {
    return item.price * item.quantity;
  };

  const calculateTotalPrice = () => {
    return state.cart.reduce((total, item) => total + calculateItemPrice(item), 0);
  };

  const payNow = (event) => {
    event.preventDefault();
    const options = {
      key: 'rzp_test_Mp5DjD6SGoo7Fk',
      amount: calculateTotalPrice() * 100,
      name: 'Hunger Saviour',
      description: 'Hunger Saviour Razorpay',
      image:'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRPp1ohLNn2RGU2lTyfORSITnkv7U8Jci4oCa-5T1DfFc5JpagoQkZ6imLo-_vrE3aJw10&usqp=CAUhttps://i.imgur.com/FApqk3D.jpeg',

      handler(response) {
        const paymentId = response.razorpay_payment_id;
        axios.post('http://localhost:8765/payments',{
                userName: username,
                transactionId: paymentId,
                amountPaid: calculateTotalPrice()
            })
            .then(res => {
                if(res.status === 201){
                    navigate('/orders');
                }
            })
            .catch(error => {
                console.error('Error in while doing payment:', error);
            });
        clearCart();
      },

      prefill: {
        name: 'Srinivas Basha',
        email: 'dssrinivas02@gmail.com',
        phone: '6303010397',
      },
      notes: {
        address: 'Hyderabad,India',
      },
      theme: {
        color: '#14213d',
      },
    };
    const rzp1 = new window.Razorpay(options);

    rzp1.open();
  };

  return (
    <div className="container cart-container mt-5 pt-5">
      {state.cart.length === 0 ? (
        <div className="d-flex align-items-center justify-content-center">
          <img src="https://www.vhv.rs/dpng/d/521-5212497_empty-cart-hd-png-download.png" alt="Empty Cart" />
        </div>
      ) : (
        <>
          {state.cart.map((item, index) => (
            <div key={index} className="cart-item-container">
              <div className="cart-item-image mb-2" style={{ maxWidth: '150px', overflow: 'hidden' }}>
                <div className="fw-bolder text-truncate">{item.menuItem}</div>
                <img src={item.menuImageUrl} alt={item.menuImageUrl} style={{ width: '100%', height: 'auto' }} />
              </div>
              <div className="cart-item-details">
                <div className="fw-bolder">₹{item.price}</div>
                <div className="item-price fw-bold">
                  Total Price: Rs.{calculateItemPrice(item).toFixed(2)}
                </div>
              </div>
              <div className="item-actions">
                <div className="cart-item-actions">
                  <button className="btn btn-success" style={{ backgroundColor: '#FC8019', borderColor: '#FC8019' }} type="button" onClick={() => decreaseQuantity(item)}>
                    <span className='fw-bold'>-</span>
                  </button>
                  <span className="quantity p-1 fw-bolder">{item.quantity}</span>
                  <button className="btn btn-success" type="button" style={{ backgroundColor: '#FC8019', borderColor: '#FC8019' }} onClick={() => increaseQuantity(item)}>
                  <span className='fw-bold'>+</span>
                  </button>
                </div>
                <button className="btn btn-icon" onClick={() => removeFromCart(item)}>
                  <span className="material-icons">delete</span>
                </button>
              </div>
            </div>
          ))}
          <div className="d-flex justify-content-center mb-4">
            <button className="btn btn-danger m-2" type="button" style={{ backgroundColor: '#FC8019', borderColor: '#FC8019' }} onClick={payNow}>
              Checkout
            </button>
          </div>
        </>
      )}
    </div>
  );
}

export default Cart;
