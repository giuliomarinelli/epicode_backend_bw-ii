import { catchError, map } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { iAccessToken } from '../Models/i-access-token';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { LoginDTO, UtenteDTO, iUtente } from '../Models/i-utente';
import { error } from 'console';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  public authSbj = new BehaviorSubject<iAccessToken | null>(null)
  token$ = this.authSbj.asObservable();
  public isLoggedIn$ = this.token$.pipe(map(u => !!u ))
  public authoritiesSbj = new BehaviorSubject<string[] | null>(null)
  public getAuthorities$: Observable<string[] | null> = this.authoritiesSbj.asObservable()
  utente!: iAccessToken;
  private jwtHelper = new JwtHelperService()
  private backendUrl: string = environment.backend





  public register(utenteDTO: UtenteDTO): Observable<iUtente | HttpErrorResponse> {
    return this.http.post<iUtente>(`${this.backendUrl}/auth/register`, utenteDTO)
  }



  public login(loginDTO: LoginDTO): Observable<iAccessToken> {
    return this.http.post<iAccessToken>(`${this.backendUrl}/auth/login`, loginDTO).pipe(map(res => {
      localStorage.setItem('accessToken', res.accessToken)
      this.authSbj.next(res)
      // Interceptor da implementare
      this.http.get<iUtente>(`${this.backendUrl}/profile`, { headers: {
        'Authorization': 'Bearer ' + res.accessToken
      } }).pipe(tap(utente => this.authoritiesSbj.next(utente.ruolo)))

      return res
    }))
  }

  public logout(): void {
    this.authSbj.next(null)
    localStorage.removeItem('accessToken')
  }

  public autoLogOutOrRefresh(token: string): void {
    const expDate = this.jwtHelper.getTokenExpirationDate(token) as Date
    const remainingMs = expDate.getTime() - new Date().getTime()
    setTimeout(this.logout, remainingMs)
    // refresh with refreshToken.....
  }

  restoreClientStatelessSession() {
    const accessToken: string | null = localStorage.getItem('accessToken')
    if (!accessToken) return
    if (this.jwtHelper.isTokenExpired(accessToken)) return
    // Interceptor da implementare
    this.http.get<iUtente>(`${this.backendUrl}/profile`, { headers: {
      'Authorization': 'Bearer ' + accessToken
    } }).pipe(tap(utente => this.authoritiesSbj.next(utente.ruolo)))
    this.autoLogOutOrRefresh(accessToken)
    this.authSbj.next({ accessToken })
  }



}
