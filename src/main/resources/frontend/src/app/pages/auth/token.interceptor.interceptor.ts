import { iAccessToken } from './../../Models/i-access-token';
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable, switchMap, take } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Injectable()
export class TokenInterceptorInterceptor implements HttpInterceptor {
  constructor(private authSrv: AuthService, private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler):Observable<HttpEvent<any>> {
    return this.authSrv.token$.pipe(switchMap((user: iAccessToken | null) => {
      if (!user) return next.handle(request)
      const newReq = request.clone(
        {
          headers: request.headers.append('Authorization', `Bearer ${user.accessToken}`)
        }
      )
        return next.handle(newReq)
    }))
  }
}
