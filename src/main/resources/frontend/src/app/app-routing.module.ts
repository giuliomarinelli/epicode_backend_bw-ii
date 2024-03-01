import { NgModule } from '@angular/core';
import { RouterModule, Routes, CanActivate } from '@angular/router';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    loadChildren: () => import('./pages/auth/login/login.module').then(m => m.LoginModule),
  },
  {
    path: 'register',
    pathMatch: 'full',
    loadChildren: () => import('./pages/auth/register/register.module').then(m => m.RegisterModule)
  },
  {
    path: 'home', loadChildren: () => import('./pages/home/home.module').then(m => m.HomeModule),
    canActivate:[AuthGuard],
    canActivateChild:[AuthGuard]
},


]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
