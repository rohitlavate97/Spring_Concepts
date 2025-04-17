import { animate, style, transition, trigger } from '@angular/animations';
import { Component, Input, OnInit, inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  animations: [
    trigger('slideInFromLeft', [
      transition(':enter', [
        style({ transform: 'translateX(-100%)' }),
        animate('1s ease-out', style({ transform: 'translateX(0%)' }))
      ])
    ])
  ]
})
export class LoginComponent implements OnInit{

  public loginForm!:FormGroup;
  hide = true;
  animationState: string = 'in';
  errorMessage:string =''
  auth!: AuthService;

  constructor(private authService: AuthService, private router: Router){
    if(authService.isLoggedIn()){
      this.router.navigate(['/dashboard']);
    }
    this.auth = authService;
  }

  ngOnInit(){
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
    this.loginForm.valueChanges.subscribe(
      next => {this.authService.errorMessageSubject.next('')}
    );
  }

  onSubmit() {
    this.authService.login(this.loginForm.value).subscribe({
      next: (v) => {
        console.log("Response from User service",v);
        this.authService.setToken(v.token);
        this.authService.setUsername(v.username);
        this.router.navigate(['/dashboard/restaurants'])
      }
    })
  }
}
