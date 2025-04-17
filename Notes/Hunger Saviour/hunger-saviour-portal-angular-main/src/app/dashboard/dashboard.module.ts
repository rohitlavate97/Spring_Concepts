import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { SharedModule } from '../shared.module';
import { RestaurantListComponent } from './restaurant-list/restaurant-list.component';
import { RestaurantComponent } from './restaurant/restaurant.component';
import { RestaurantMenuComponent } from './restaurant/restaurant-menu/restaurant-menu.component';
import { CartComponent } from './cart/cart.component';


@NgModule({
  declarations: [
    DashboardComponent,
    RestaurantListComponent,
    RestaurantComponent,
    RestaurantMenuComponent,
    CartComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    SharedModule
  ]
})
export class DashboardModule { }
