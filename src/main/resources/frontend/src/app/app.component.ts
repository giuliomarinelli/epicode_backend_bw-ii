import { UtenteDTO, iUtente } from './Models/i-utente';
import { Component } from '@angular/core';
import { Router, RoutesRecognized } from '@angular/router';
import { AuthService } from './services/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { on } from 'events';


@Component({
  selector: '#root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  constructor(private router: Router, private authSvc: AuthService) { }

  protected isHome: boolean = true

  private userDTO: UtenteDTO = {
    nome: 'Giulio',
    cognome: 'Marinelli',
    email: 'mariucscddioOoOoOOOOo@rossi.it',
    password: 'abcdefg',
    username: 'blablasvddnxskjnask'

  }
  private onlyOnce = true
  ngOnInit() {
    this.router.events.subscribe(e => {
      if (e instanceof RoutesRecognized) {
        if (e.url !== '/' || !e.url) {
          this.isHome = false
        }
      }
    })


  }


}
