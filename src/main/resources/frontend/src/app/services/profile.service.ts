import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable, map } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UtenteDTO, iUtente } from '../Models/i-utente';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private authSvc: AuthService, private http: HttpClient) {  }

  private endpoint: string = `${environment.backend}/profile`

  public getMyProfile(): Observable<iUtente | HttpErrorResponse | null> {
    let isLoggedIn!: boolean
    this.authSvc.isLoggedIn$.subscribe(res => isLoggedIn = res)
    return this.http.get<iUtente>(this.endpoint).pipe(map(res => {
      return isLoggedIn ? res : null
    }))
  }

  public updateMyProfile(utenteDTO: UtenteDTO): Observable<iUtente | HttpErrorResponse | null> {
    let isLoggedIn!: boolean
    this.authSvc.isLoggedIn$.subscribe(res => isLoggedIn = res)
    return this.http.put<iUtente>(this.endpoint, utenteDTO).pipe(map(res => {
      return isLoggedIn ? res : null
    }))
  }

  public deleteMyProfile(): Observable<Object | null> {
    let isLoggedIn!: boolean
    this.authSvc.isLoggedIn$.subscribe(res => isLoggedIn = res)
    return this.http.delete(this.endpoint).pipe(map(res => {
      return isLoggedIn ? res : null
    }))
  }


}
