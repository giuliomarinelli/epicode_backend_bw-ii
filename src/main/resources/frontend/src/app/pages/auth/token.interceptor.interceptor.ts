import { iAccessToken } from './../../Models/i-access-token';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable, switchMap, take } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { isPlatformBrowser, isPlatformServer } from '@angular/common';

@Injectable()
export class TokenInterceptorInterceptor implements HttpInterceptor {
  constructor(private authSrv: AuthService, private router: Router, @Inject(PLATFORM_ID) private platformId: String) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<any>> {

    return this.authSrv.token$.pipe(switchMap((user: iAccessToken | null) => {
      if (!user && isPlatformBrowser(PLATFORM_ID)) return next.handle(request)
      const newReq = request.clone(
        {
          headers: request.headers.append('Authorization', `Bearer ${user?.accessToken}`)
        }
      )
      if (isPlatformBrowser(PLATFORM_ID)) {
        return next.handle(newReq)
      } else return next.handle(request)
    }))

  }
}
