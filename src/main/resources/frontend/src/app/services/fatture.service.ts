import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { log } from 'console';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { environment } from '../../environments/environment.development';
import { iFattura } from '../Models/i-fattura';

@Injectable({
  providedIn: 'root'
})
export class FattureService {

  private backendUrl: string = environment.backend
  private token!:string|null;
  isLoggedIn!:boolean;

  constructor(private authSv:AuthService, private http: HttpClient, private router: Router) {
  }

  getAllFatture():Observable<iFattura[]>{
    if (this.getToken === null) {
      alert("no token available")
    }
     return this.http.get<iFattura[]>(`${this.backendUrl}/api/fatture`,{
        headers:{
          "Authorization":`Bearer ${this.getToken()}`
        },
      })
  }



  getToken(): string|null {
    return localStorage.getItem('accessToken');

}

}
