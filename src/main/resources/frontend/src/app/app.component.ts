import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { Router, RoutesRecognized } from '@angular/router';
import { AuthService } from './services/auth.service';
import { isPlatformBrowser } from '@angular/common';


@Component({
  selector: '#root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  constructor(private router: Router, private authSvc: AuthService, @Inject(PLATFORM_ID) private platformId: string) { }

  protected isHome: boolean = true

  protected contentLoaded = false

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
    })


  }

  protected setContentLoaded(event: boolean): void {
    this.contentLoaded = event
  }


}
