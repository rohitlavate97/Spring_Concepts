import { Component, OnInit } from '@angular/core';
import { RESTAURANTS } from '../../../db/restaurants';

import { Router } from '@angular/router';
import { trigger, transition, style, animate } from '@angular/animations';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-restaurant-list',
  templateUrl: './restaurant-list.component.html',
  styleUrls: ['./restaurant-list.component.scss'],
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('1s', style({ opacity: 1 })),
      ]),
      transition(':leave', [
        animate('1s', style({ opacity: 0 })),
      ]),
    ]),
    trigger('scaleIn', [
      transition(':enter', [
        style({ transform: 'scale(0.8)', opacity: 0 }),
        animate('1.5s ease-out', style({ transform: 'scale(1)', opacity: 1 })),
      ]),
    ]),
  ],
})
export class RestaurantListComponent implements OnInit{

    restaurants = RESTAURANTS;  // Do a http call to get all restaurants from Backend database

    constructor(private router: Router){
    }

    ngOnInit(): void {
      // Make an API call to get all restaurants....
    }

}
