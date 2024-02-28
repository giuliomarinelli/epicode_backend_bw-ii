import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';

import { AuthService } from '../../../services/auth.service';

  @Component({
    selector: '#login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
  })
  export class LoginComponent {
    isLoading = false;
    userRole$ = new BehaviorSubject<string>('');
    authSrv: any;

    constructor( private authSvc: AuthService, private router: Router){}

    // un utente può avere uno o piu ruoli, va gestita diversamente

    private ruoli!: string[]

    ngOnInit() {
      this.authSvc.getAuthorities$.subscribe(res => {
        res ? this.ruoli = res : this.ruoli = []
      })
    }

    access(form: NgForm) {
      this.isLoading = true;

      this.authSrv.login(form.value).subscribe(
        (response: any) => {
          const token = response.token;
          if (token) {
            localStorage.setItem('token', token);
            this.authSrv.getUserDetails().subscribe(
              (user: { ruolo: { nome: any; }; username: any; id: any; foto: any; }) => {
                const userRole = user?.ruolo?.nome;
                const username = user?.username;
                const id = user?.id;
                const foto = user?.foto;
                localStorage.setItem('userRole', userRole);
                localStorage.setItem('username', username);
                localStorage.setItem('id', id);

                // this.roleSrv.setUserRole(userRole);
                this.userRole$.next(userRole);

                if (userRole === 'ADMIN') {
                  this.router.navigate(['/admin']);
                } else if (userRole === 'USER') {
                  this.router.navigate(['/blog']);
                } else {
                  console.log('Ruolo non gestito:', userRole);
                }

                this.isLoading = false;
              },
              (error: any) => {
                console.log('Errore nel recuperare i dettagli dell\'utente:', error);
                this.isLoading = false;
              }
            );
          } else {
            console.log('Il server non ha restituito un token.');
            this.isLoading = false;
          }
        },
        (error: { error: string; }) => {
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
      );
    }
  }

