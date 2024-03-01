import { Component } from '@angular/core';
import { ClientiService } from '../../services/clienti.service';
import { iCliente } from '../../Models/i-cliente';

@Component({
  selector: 'app-clienti',
  templateUrl: './clienti.component.html',
  styleUrl: './clienti.component.scss'
})
export class ClientiComponent {
  clienti: iCliente[] = [];

  constructor(private clientiSv: ClientiService) { }

  ngOnInit(): void {
    this.getAllClienti();
  }

  getAllClienti(): void {
    this.clientiSv.getAllClienti().subscribe((response: iCliente[]) => {
      this.clienti = response;
    });
  }
}
