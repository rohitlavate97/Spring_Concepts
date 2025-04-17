import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, catchError, of, throwError } from 'rxjs';
import { AuthRequest } from '../model/AuthRequest';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public errorMessageSubject = new BehaviorSubject<String>('');
  errorMessage$ : Observable<String> = this.errorMessageSubject.asObservable();

  constructor(private router:Router, private httpClient:HttpClient) { }

  setToken(token:string):void {
    localStorage.setItem('token',token);
  }

  setUsername(username:string){
    console.log("Auth Service",username);
    localStorage.setItem('username', username);
  }

  getUsername() {
    return localStorage.getItem('username');
  }

  getToken(): string | null{
    return localStorage.getItem('token');
  }

  isLoggedIn(){
    return this.getToken()!==null;
  }

  logout(){
    localStorage.removeItem('token');
    this.router.navigate(['/']);
  }

  login(request: AuthRequest): Observable<any>{
    return this.httpClient
              .post<any>('http://localhost:8765/users/login', request)
              .pipe(
                    catchError((error: any) => {
                      console.log(error)
                      if (error.status === 401) {
                        return throwError(() => new Error('Invalid Credentials'));
                      } else {
                        return throwError(() => new Error('An unexpected error occurred'));
                      }
                    })
              );
  }
}
