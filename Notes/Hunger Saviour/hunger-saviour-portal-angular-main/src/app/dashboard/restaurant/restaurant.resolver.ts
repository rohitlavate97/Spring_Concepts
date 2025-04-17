import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Restaurant } from 'src/app/model/Restaurant';

export const restaurantResolver: ResolveFn<Observable<any>> = (route, state) => {
  
  return inject(HttpClient)
            .get("http://localhost:8765/restaurants/"+Number(route.paramMap.get('restaurantId')));
};
