import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FattureRoutingModule } from './fatture-routing.module';
import { FattureComponent } from './fatture.component';


@NgModule({
  declarations: [
    FattureComponent
  ],
  imports: [
    CommonModule,
    FattureRoutingModule
  ]
})
export class FattureModule { }
