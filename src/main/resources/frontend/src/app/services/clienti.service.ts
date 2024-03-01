import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpClient } from '@angular/common/http';
import { Router } from 'express';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { iCliente } from '../Models/i-cliente';

@Injectable({
  providedIn: 'root'
})
export class ClientiService {

  private backendUrl: string = environment.backend
  isLoggedIn!:boolean;

  constructor(private authSv:AuthService, private http: HttpClient) {

  }

  getAllClienti():Observable<iCliente[]>{
    if (this.getToken === null) {
      alert("no token available")
    }
     return this.http.get<iCliente[]>(`${this.backendUrl}/api/clienti`,{
        headers:{
          "Authorization":`Bearer ${this.getToken()}`
        },
      })
  }



  getToken(): string|null {
    return localStorage.getItem('accessToken');

}
}
