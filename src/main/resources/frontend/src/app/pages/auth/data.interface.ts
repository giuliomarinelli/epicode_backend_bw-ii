export interface AuthData {
  [x: string]: any;

  token: string;
  utente: {
    id: string;
    nome: string;
    cognome: string;
    eta: number;
    email: string;
    password: string;
    ruolo: {
      nome: string;
      ruolo_admin: boolean;
      ruolo_user: boolean;
    }
  }
}
