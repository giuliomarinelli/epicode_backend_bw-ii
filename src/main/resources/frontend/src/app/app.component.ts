import { Component } from '@angular/core';
import { Router, RoutesRecognized } from '@angular/router';


@Component({
  selector: '#root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  constructor(private router: Router) { }

  protected isHome: boolean = true

  ngOnInit() {
    this.router.events.subscribe(e => {
      if (e instanceof RoutesRecognized) {
        if (e.url !== '/' || !e.url ) {
          this.isHome = false
        }
      }
    })
  }

}
