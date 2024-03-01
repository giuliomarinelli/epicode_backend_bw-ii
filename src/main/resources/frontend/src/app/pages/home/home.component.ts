import { isPlatformBrowser } from '@angular/common';
import { Component, Inject, PLATFORM_ID } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  constructor(@Inject(PLATFORM_ID) private platformId: string){}


  get isBrowserOnly(): boolean {
    return isPlatformBrowser(this.platformId);
  }

}
