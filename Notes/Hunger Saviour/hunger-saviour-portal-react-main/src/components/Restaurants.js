import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom'; // Import Link
import StarsIcon from '@mui/icons-material/Stars';
import '../index.css';

function Restaurants() {
  const [restaurants, setRestaurants] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8765/restaurants/all")
      .then(res => {
        setRestaurants(res.data);
      })
      .catch(error => {
        console.error('Error fetching restaurants:', error);
      });
  }, []);

  return (
    <div className="d-flex flex-wrap justify-content-center my-5 pt-3">
      {restaurants.map(rest => (
        <Link key={rest.restaurantId} to={`/restaurants/${rest.restaurantId}`} className="card-link" style={{ textDecoration: 'none' }}>
          <div className="card border-0 shadow-none mx-2 my-2" style={{ width: '250px', cursor: 'pointer' }}>
            <div style={{ height: '150px', overflow: 'hidden' }}>
              <img src={rest.imageUrl} alt="Restaurant" 
                style={{ width: '100%', height: '100%', objectFit: 'cover', objectPosition: 'top' }}/>
            </div>
            <div className="card-body" style={{ height: '120px' }}>
              <div className="d-flex flex-column">
                <div className="fw-bolder text-truncate">{rest.restaurantName}</div>
                <div className="d-flex flex-row fw-bold"><span style={{ color: '#60b246' }}><StarsIcon /></span>{rest.rating}</div>
                <div className="fw-medium text-truncate">{rest.menuTypes.join(', ')}</div>
                <div className="fw-normal text-secondary">{rest.location}</div>
              </div>
            </div>
          </div>
        </Link>
      ))}
    </div>
  );
}

export default Restaurants;
