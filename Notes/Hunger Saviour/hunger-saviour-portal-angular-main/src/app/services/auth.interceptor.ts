import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse
} from '@angular/common/http';
import { Observable, catchError, map, of } from 'rxjs';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log("Interceptor called");
    const token = this.authService.getToken();
    if(token){

      const cloned = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    return next.handle(cloned).pipe(map<HttpEvent<any>, any>((evt: HttpEvent<any>) => {
      if (evt instanceof HttpResponse) {
        
      }
      return evt;
    }))
    .pipe(catchError(err => {
      this.router.navigate(['/auth/service-unavailable']);
      return of(err)
    }));
    }else{
      return next.handle(request).pipe(catchError(err => {
        if(err.status === 401){
          this.authService.errorMessageSubject.next('Invalid Credentials');
          console.log("Invalid Credentials");
        }else{
          console.log("Error Interceptor");
          this.router.navigate(['/auth/service-unavailable']);
        }
        return of(err)
      }));
    }
  }
}