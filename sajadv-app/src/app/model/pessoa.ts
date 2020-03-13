export class Pessoa {

  id: number;
  nome: string;
  cpf: string;
  email: string;
  dataNascimento: Date;

  constructor(id: number, nome: string, cpf: string, email: string, dataNascimento: Date) {
    this.id = id;
    this.nome = nome;
    this.cpf = cpf;
    this.email = email;
    this.dataNascimento = dataNascimento;
  }

}
