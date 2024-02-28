import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    loadChildren: () => import('./pages/documentazione/auth/home/home.module').then(m => m.HomeModule)
  },
  { path: 'auth', loadChildren: () => import('./pages/documentazione/auth/auth.module').then(m => m.AuthModule) },
  { path: 'auth', loadChildren: () => import('./pages/documentazione/auth/auth.module').then(m => m.AuthModule) },
  { path: 'auth/register', loadChildren: () => import('./auth/register/register.module').then(m => m.RegisterModule) },
  { path: 'register', loadChildren: () => import('./register/register.module').then(m => m.RegisterModule) }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
