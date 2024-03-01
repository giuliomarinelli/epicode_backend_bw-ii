import { Component } from '@angular/core';
import { FattureService } from '../../services/fatture.service';
import { iFattura } from '../../Models/i-fattura';

@Component({
  selector: 'app-fatture',
  templateUrl: './fatture.component.html',
  styleUrl: './fatture.component.scss'
})
export class FattureComponent {
  fatture: iFattura[] = [];

  constructor(private fattureSv: FattureService) { }

  ngOnInit(): void {
    this.getAllFatture();
  }

  getAllFatture(): void {
    this.fattureSv.getAllFatture().subscribe((response: iFattura[]) => {
      console.log(response);
      this.fatture = response;
    });
  }
}
