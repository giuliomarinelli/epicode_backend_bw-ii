import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DocumentazioneRoutingModule } from './documentazione-routing.module';
import { DocumentazioneComponent } from './documentazione.component';


@NgModule({
  declarations: [
    DocumentazioneComponent
  ],
  imports: [
    CommonModule,
    DocumentazioneRoutingModule
  ]
})
export class DocumentazioneModule { }
