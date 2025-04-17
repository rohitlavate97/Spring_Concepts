import { Component, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, pluck, tap } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Restaurant } from 'src/app/model/Restaurant';
import { RestaurantMenu } from 'src/app/model/RestaurantMenu';
import { trigger, transition, style, animate, stagger, query } from '@angular/animations';
import { CartService } from '../../cart/services/cart.service';

@Component({
  selector: 'app-restaurant-menu',
  templateUrl: './restaurant-menu.component.html',
  styleUrls: ['./restaurant-menu.component.scss'],
  animations: [
    trigger('itemAnimation', [
      transition(':enter', [
        query(':self', [style({ opacity: 0, transform: 'translateY(-30px)' })]),
        query(':self', [
          stagger('500ms', [
            animate('500ms ease-in', style({ opacity: 1, transform: 'translateY(0)' }))
          ])
        ])
      ])
    ])
  ]
})
export class RestaurantMenuComponent implements OnDestroy {

  rest$?: Observable<Restaurant | null>;
  totalCartPrice: number = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private _snackBar: MatSnackBar,
    private cartService: CartService
  ) {
    this.openSnackBar();
  }

  ngOnInit() {
    this.rest$ = this.route.data.pipe(pluck('rest'));
    this.rest$.subscribe(v => console.log("hi", v));
    console.log("Menu component", this.totalCartPrice);
  }

  ngOnDestroy(): void {
    this._snackBar.dismiss();
  }

  addToCart(menu: RestaurantMenu, restaurantId: number) {
    menu.quantity = menu.quantity ? menu.quantity + 1 : 1;
    menu.restaurantId=restaurantId;
    this.cartService.addToCart(menu);
    this.updateTotalPrice();
    this.openSnackBar();
  }

  increaseQuantity(menu: RestaurantMenu) {
    menu.quantity = menu.quantity ? menu.quantity + 1 : 1;
    this.cartService.updateCart(menu);
    this.updateTotalPrice();
  }

  decreaseQuantity(menu: RestaurantMenu) {
    if (menu.quantity && menu.quantity > 0) {
      menu.quantity--;
      this.cartService.updateCart(menu);
      this.updateTotalPrice();
    } else {
      this._snackBar.dismiss();
    }
  }

  updateTotalPrice() {
    this.rest$!.pipe(
      tap(restaurant => {
        if (restaurant && restaurant.menuItems) {
          this.totalCartPrice = restaurant.menuItems.reduce((total, item) => total + item.price * (item.quantity || 0), 0);
        }
      })
    ).subscribe();
    this.openSnackBar();
  }

  openSnackBar() {
    if (this.totalCartPrice > 0) {
      this._snackBar.open(`Total Price: Rs.${this.totalCartPrice.toFixed(2)}`, 'View Cart')
        .onAction().subscribe(() => {
          // Handle the action (e.g., navigate to the cart page)
          this.router.navigateByUrl('dashboard/cart');
        });
    } else {
      // If totalCartPrice is zero, close the snackbar
      this._snackBar.dismiss();
    }
  }
}