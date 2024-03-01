export interface iCliente {
  id: string
  ragioneSociale: String
  partitaIva: number
  email: string
  dataInserimento: Date
  dataUltimoContatto: Date
  fatturatoAnnuale: number
  pec: string
  telefono: number
  emailContatto: number
  nomeContatto: string
  cognomeContatto: string
  telefonoContatto: number
  logoAziendale: string
  tipoCliente: string
  sede: iIndirizzo
}

export type ClienteDTO = Omit<iCliente, 'id'>

export interface iIndirizzo {
  via: string
  civico: string
  localita?: string
  cap: number
  nomeComune: string
  tipoSede: string
  siglaProvincia: string
}
