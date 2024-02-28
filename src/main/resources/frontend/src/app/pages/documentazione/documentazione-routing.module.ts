import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DocumentazioneComponent } from './documentazione.component';

const routes: Routes = [{ path: '', component: DocumentazioneComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DocumentazioneRoutingModule { }
