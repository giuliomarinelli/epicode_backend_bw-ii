import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { log } from 'console';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  isLoading = false;
  authSrv: any;


  constructor( private router: Router) {}

  registra(form: NgForm) {
    this.isLoading = true;

    try {
      const userDetails = {
        nome: form.value.nome,
        cognome: form.value.cognome,
        eta: form.value.eta,
        email: form.value.email,
        password: form.value.password
      };

      this.authSrv.signup(userDetails).subscribe(
        () => {
          this.router.navigate(['auth/login']);
          this.isLoading = false;
        },
        (error: { error: string; }) => {
          console.error(error.error);
          if (error.error === 'Email format is invalid') {
            console.log("Formato email non valido!");
          } else if (error.error === 'Email already exists') {
            console.log('Email già in uso!')
          } else if (error.error === 'Password is too short') {
            console.log('Password troppo corta!')
          }

          this.isLoading = false;
        }
      );
    } catch (error) {
      console.error(error);
      this.isLoading = false;
    }
  }
}
