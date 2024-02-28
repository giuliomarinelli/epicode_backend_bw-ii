import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginDTO } from '../../Models/i-utente';

@Component({
  selector: '#home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  loginForm: FormGroup = this.fb.group({
    username: [null, [Validators.required]],
    password: [null, [Validators.required]]
  })

  protected color: string = ''

  protected valid!: boolean
  protected msg!: string
  protected emailMsg: string = ''
  protected passwordMsg: string = ''
  protected errorMsg!: LoginDTO

  constructor(private fb: FormBuilder, private router: Router) { }

  ngDoCheck() {
    this.errorMsg = {
      username: this.setInvalidMessages('email'),
      password: this.setInvalidMessages('password')
    }
    this.msg = `${this.errorMsg.username}. ${this.errorMsg.password}`
    this.valid = this.loginForm['valid']
  }


  public setInvalidMessages(fieldName: string): string {
    const field: AbstractControl | null = this.loginForm.get(fieldName)
    let errorMsg = ''
    if (field) {
      if (field.errors) {
        if (field.errors['required'] && fieldName === 'email') errorMsg += 'Email obbligatoria. '
        if (field.errors['required'] && fieldName === 'password') errorMsg += 'Password obbligatoria. '
        if (field.errors['email']) errorMsg += 'Formato email errato'
      }

    }
    return errorMsg
  }

  public isValid(fieldName: string): boolean | undefined {
    return this.loginForm.get(fieldName)?.valid && this.loginForm.get(fieldName)?.dirty
  }

  public isInvalid(fieldName: string): boolean | undefined {
    return !this.loginForm.get(fieldName)?.valid && this.loginForm.get(fieldName)?.dirty
  }

  public logIn(): void {
    if (this.loginForm.valid) {
      // Form validato => gestire login
    } else {
      Object.values(this.loginForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }




  showModal1(): void {
    this.isVisible1 = true
  }

  handleOk1(): void {
    this.isVisible1 = false
  }
  isVisible1 = false
  isVisible2 = false
  showModal2(): void {
    this.isVisible2 = true
  }

  handleOk2(): void {
    this.isVisible2 = false
  }


}
