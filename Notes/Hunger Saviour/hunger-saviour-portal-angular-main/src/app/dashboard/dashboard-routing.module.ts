import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { RestaurantListComponent } from './restaurant-list/restaurant-list.component';
import { restaurantResolver } from './restaurant/restaurant.resolver';
import { RestaurantMenuComponent } from './restaurant/restaurant-menu/restaurant-menu.component';
import { CartComponent } from './cart/cart.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    children: [
      {
        path: 'restaurants',
        component: RestaurantListComponent,
      },
      {
        path: 'restaurants/:restaurantId',
        component: RestaurantMenuComponent,
        resolve: {
          rest: restaurantResolver
        }
      },
      {
        path: 'cart',
        component: CartComponent
      },
      {
        path: '',
        redirectTo: 'restaurants',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
