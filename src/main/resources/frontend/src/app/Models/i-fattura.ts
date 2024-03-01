export interface iFattura {
  numero: number
  data: Date
  importo: number
  stato: string
  clienteId: string
}

export type FatturaDTO = Omit<iFattura, 'numero'>