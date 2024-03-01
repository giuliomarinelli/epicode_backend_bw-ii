import { Injectable } from '@angular/core';
import { Subject, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoadService {

  constructor() { }

  firstLoad = new BehaviorSubject<boolean>(true)
  firstLoad$ = this.firstLoad.asObservable()
  isLoading = new Subject<boolean>()
  isLoading$ = this.isLoading.asObservable()
}
