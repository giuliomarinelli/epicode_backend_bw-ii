export interface iUtente {
  id: string
  username: string
  email: string
  nome: string
  cognome: string
  avatar?: string
  createdAt: string
  ruolo: string[]
  enabled: boolean
  accountNonExpired: boolean
  credentialsNonExpired: boolean
  accountNonLocked: boolean
}

export type UtenteDTO = Omit<iUtente, 'id' | 'avatar' | 'createdAt' | 'ruolo' | 'enabled' | 'accountNonExpired' | 'credentialsNonExpired' | 'accountNonLocked'>

export interface LoginDTO {
  username: string
  password: string
}
