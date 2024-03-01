import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';

import { AuthService } from '../../../services/auth.service';
import { LoginDTO } from '../../../Models/i-utente';
import { iAccessToken } from '../../../Models/i-access-token';

  @Component({
    selector: '#login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
  })
  export class LoginComponent {
    isLoading = false;
    userRole$ = new BehaviorSubject<string>('');


    constructor( private authSvc: AuthService, private router: Router){}

    // un utente può avere uno o piu ruoli, va gestita diversamente

    private ruoli!: string[]



    access(form: NgForm) {
      this.isLoading = true;

      this.authSvc.login(form.value).subscribe(
        (response: iAccessToken) => {
          this.authSvc.getAuthorities$.subscribe(res => {
            res ? this.ruoli = res : this.ruoli = []
          })
          this.router.navigate(['/home'])
                this.isLoading = false;
              },
              (error: any) => {
                console.log('Errore nel recuperare i dettagli dell\'utente:', error);
                this.isLoading = false;
              }
            );
          (error:any) => {
          console.log(error);

          if (error.error === 'Incorrect password') {
            console.log('Occhio, pirata! Hai sbagliato password!');
          } else if (error.error === 'Cannot find user') {
            console.log('Registrati!')
              this.router.navigate(['auth/register']);

          } else {
            console.log("Errore: Prova ad effettuare una nuova registrazione!")
              this.router.navigate(['auth/register']);
          }
          this.isLoading = false;
        }
    }
  }


