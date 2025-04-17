import { Component, inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  constructor(){
    console.log('dashboard component called')
    if(inject(AuthService).isLoggedIn()){
      inject(Router).navigate(['/dashboard']);
    }
  }
}
