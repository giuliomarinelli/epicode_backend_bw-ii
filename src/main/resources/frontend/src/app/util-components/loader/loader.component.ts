import { Component, EventEmitter, Output } from '@angular/core';
import { NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router, RoutesRecognized } from '@angular/router';
import { LoadService } from '../../services/load.service';

@Component({
  selector: '#viewport-loader',
  templateUrl: './loader.component.html',
  styleUrl: './loader.component.scss'
})
export class LoaderComponent {
  dNone: string = ''
  fadeOut: string = ''
  isHome: boolean = false
  bg: string = ''

  @Output() onContentLoaded = new EventEmitter<boolean>()

  constructor(private router: Router, private loadSvc: LoadService) {

    // this.disableScrolling()

    router.events.subscribe((event) => {
      if (event instanceof RoutesRecognized) {
        const path = event.urlAfterRedirects
        path !== '/it' ? this.isHome = false : this.isHome = true
      } else if (event instanceof NavigationStart) {
        this.loadSvc.firstLoad$.subscribe(res => {
          if (res) {
            this.dNone = ''
            this.fadeOut = ''
            this.bg = 'first-load-bg'          }
        })
      } else if (event instanceof NavigationEnd) {
        this.endLoading()
      } else if (event instanceof NavigationCancel) {
        this.endLoading()
      } else if (event instanceof NavigationError) {
        this.endLoading()
      }
    })
  }

  // private disableScrolling(): void {
  //   document.body.style.overflowY = 'hidden'
  // }

  // private enableScrolling(): void {
  //   document.body.style.overflowY = 'auto'
  // }

  private endLoading(): void {
    this.onContentLoaded.emit(true)
    this.fadeOut = 'fade-out-animation'
    this.loadSvc.firstLoad.next(false)
    setTimeout(() => {
      // this.enableScrolling()
      this.dNone = 'd-none'
    }, 150)
  }

}
