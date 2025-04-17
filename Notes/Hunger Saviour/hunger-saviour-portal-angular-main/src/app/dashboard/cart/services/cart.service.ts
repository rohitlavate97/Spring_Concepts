import { Injectable } from '@angular/core';
import { RestaurantMenu } from 'src/app/model/RestaurantMenu';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartKey = 'cartItems';

  constructor() {}

  get cartItems(): RestaurantMenu[] {
    const cartItemsString = sessionStorage.getItem(this.cartKey);
    return cartItemsString ? JSON.parse(cartItemsString) : [];
  }

  set cartItems(items: RestaurantMenu[]) {
    const cartItemsString = JSON.stringify(items);
    sessionStorage.setItem(this.cartKey, cartItemsString);
  }

  addToCart(item: RestaurantMenu) {
    let currentCartItems = this.cartItems;
    const existingItem = currentCartItems.find(cartItem => cartItem.restaurantMenuId === item.restaurantMenuId);

    if (existingItem) {
      existingItem.quantity += 1;
    } else {
      const newItem = { ...item, quantity: 1 };
      currentCartItems.push(newItem);
    }

    this.cartItems = currentCartItems;
  }

  removeFromCart(item: RestaurantMenu) {
    let currentCartItems = this.cartItems;
    currentCartItems = currentCartItems.filter(cartItem => cartItem.restaurantMenuId !== item.restaurantMenuId);
    this.cartItems = currentCartItems;
  }

  updateCart(item: RestaurantMenu) {
    let currentCartItems = this.cartItems;
    currentCartItems = currentCartItems.map(cartItem => {
      if (cartItem.restaurantMenuId === item.restaurantMenuId) {
        return { ...cartItem, quantity: item.quantity };
      }
      return cartItem;
    });

    this.cartItems = currentCartItems;
  }

  clearCart() {
    this.cartItems = [];
  }
}
