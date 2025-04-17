import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RestaurantMenu } from 'src/app/model/RestaurantMenu';
import { CartService } from './services/cart.service';
import { AuthService } from 'src/app/services/auth.service';
import { Order } from 'src/app/model/Order';
import { HttpClient } from '@angular/common/http';
declare var Razorpay: any;

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  cartItems: RestaurantMenu[] = [];
  totalCartPrice: number = 0;

  constructor(private cartService: CartService, private router: Router,
    private authService: AuthService, private httpClient: HttpClient) {}

  ngOnInit(): void {
    // Initialize cart items from sessionStorage
    this.cartItems = this.cartService.cartItems;
    this.updateTotalPrice();
  }

  removeFromCart(item: RestaurantMenu) {
    this.cartService.removeFromCart(item);
    this.cartItems = this.cartService.cartItems; // Update cart items after removal
    this.updateTotalPrice();
  }

  increaseQuantity(item: RestaurantMenu) {
    item.quantity = item.quantity ? item.quantity + 1 : 1;
    this.cartService.updateCart(item);
    this.cartItems = this.cartService.cartItems; // Update cart items after quantity increase
    this.updateTotalPrice();
  }

  decreaseQuantity(item: RestaurantMenu) {
    if (item.quantity && item.quantity > 1) {
      item.quantity--;
      this.cartService.updateCart(item);
      this.cartItems = this.cartService.cartItems; // Update cart items after quantity decrease
      this.updateTotalPrice();
    } else if (item.quantity === 1) {
      this.removeFromCart(item);
    }
  }

  updateTotalPrice() {
    this.totalCartPrice = this.cartItems.reduce(
      (total, item) => total + item.price * (item.quantity || 0),
      0
    );
  }

  calculateItemPrice(item: RestaurantMenu): number {
    return item.price * (item.quantity || 1);
  }

  payNow() {
    const RozarpayOptions: any = {
      description: 'Sample Razorpay demo',
      currency: 'INR',
      amount: this.totalCartPrice * 100,
      name: 'Hunger Saviour',
      key: 'rzp_test_Mp5DjD6SGoo7Fk',
      image:
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRPp1ohLNn2RGU2lTyfORSITnkv7U8Jci4oCa-5T1DfFc5JpagoQkZ6imLo-_vrE3aJw10&usqp=CAUhttps://i.imgur.com/FApqk3D.jpeg',
      prefill: {
        name: 'Srinivas Basha',
        email: 'dssrinivas02@gmail.com',
        phone: '6303010397',
      },
      theme: {
        color: '#14213d',
      },
      modal: {
        ondismiss: () => {
          console.log('dismissed');
        },
      },
    };

    // success callback
    const callback = (paymentid: any) => {
      this.router.navigate(['dashboard/cart']);
      console.log(paymentid);
      console.log(this.authService.getUsername());
      const orderData: Order = {
        username: this.authService.getUsername(),
        totalPrice: this.totalCartPrice,
        transactionId: paymentid.razorpay_payment_id,
        orderJson: JSON.stringify(this.cartItems)
      }
      //call payment service to insert the data(payment id, restaurant json) in payments table
      // From the payment service, we need to write KafkaPublisher to send the data to order service
      // Orderservice will have the listener to get the order data including txnid, order data, username, totalprice
      console.log('calling payment api');
      this.httpClient
          .post('http://localhost:8765/payments', orderData)
          .subscribe({
            next: v => console.log("Payment Done Successfully")
          });
      this.cartItems = [];
      this.cartService.clearCart();
    };
    
    // failure callback(Home work)
    const errorCallback = (error: any) => {
      console.error('Payment failed or error:', error);
      this.router.navigate(['dashboard/cart']);

    };
    RozarpayOptions['handler'] = callback.bind(this);
    const rzp = new Razorpay(RozarpayOptions);
    rzp.on('payment.failed', errorCallback);
    rzp.open();
  }
}
