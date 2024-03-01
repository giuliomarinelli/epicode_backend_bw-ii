import { UtenteDTO, iUtente } from './Models/i-utente';
import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { Router, RoutesRecognized, NavigationEnd, Event as NavigationEvent } from '@angular/router';
import { AuthService } from './services/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { filter, firstValueFrom } from 'rxjs';
import { on } from 'events';
import { isPlatformBrowser } from '@angular/common';
import { log } from 'console';


@Component({
  selector: '#root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  constructor(private router: Router, private authSvc: AuthService, @Inject(PLATFORM_ID) private platformId: string) { }

  protected isHome: boolean = true
  protected contentLoaded = false
  protected isInLoginPage!:boolean;
  protected isInloggedIn!:boolean

  get isBrowserOnly(): boolean {
    return isPlatformBrowser(this.platformId);
  }
  ngOnInit() {
    this.router.events.subscribe(e => {
      if (e instanceof RoutesRecognized) {
        if (e.url !== '/' || !e.url) {
          this.isHome = false
        }
      }

      this.authSvc.isLoggedIn$.subscribe((isIn) =>{
        this.isInloggedIn = isIn
      })


    })

    this.router.events
    .pipe(filter((event: NavigationEvent): event is RoutesRecognized => event instanceof RoutesRecognized))
    .subscribe((event: RoutesRecognized) => {
      if (event.url.includes('/register')) {
        this.isInLoginPage = false
      }else {
        this.isInLoginPage = true
      }
    });

  }

  logout(){
    this.authSvc.logout()
  }

  protected setContentLoaded(event: boolean): void {
    this.contentLoaded = event
  }


}
