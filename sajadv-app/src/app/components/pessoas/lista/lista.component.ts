import { Component, OnInit } from '@angular/core';

import { ToastrService } from 'ngx-toastr';

import { Pessoa } from 'src/app/model/pessoa';
import { PessoasService } from 'src/app/services/pessoas.service';

@Component({
  selector: 'app-lista',
  templateUrl: './lista.component.html',
  styleUrls: ['./lista.component.css']
})
export class ListaComponent implements OnInit {

  page = 1;
  items = 10;

  pessoa: Pessoa;
  pessoas: Pessoa[];

  title: string;
  message: string;

  constructor(private toastr: ToastrService,
              private pessoasService: PessoasService) { }

  ngOnInit() {
    this.setarValoresModal();
    this.setarListaPessoas();
  }

  setarListaPessoas(): void {
    try {
      this.pessoasService.findAll().subscribe((response) => {
        this.pessoas = response.content;
      }, (error: Error) => {
        console.log(error);
        this.toastr.error(`Ocorreu um erro ao buscar lista de Pessoas cadastradas: ${error.message}`);
      });
    } catch (err) {
      console.log(err);
      this.toastr.error('Ocorreu um erro inesperado ao buscar lista de Pessoas cadastradas: ' + err);
    }
  }

  setarPessoa(pessoa: Pessoa): void {
    this.pessoa = pessoa;
  }

  deletarRegistro(): void {
    try {
      this.pessoasService.delete(this.pessoa.id).subscribe(() => {
        this.pessoas = this.pessoas.filter(p => p !== this.pessoa);
        this.toastr.success(`Registro de ${this.pessoa.nome} deletado com sucesso!`);
      }, (error: Error) => {
        console.log(error);
        this.toastr.error(`Ocorreu um erro ao deletar registro de ${this.pessoa.nome}: ${error.message}`);
      });
    } catch (err) {
      console.log(err);
      this.toastr.error(`Ocorreu um erro inesperado ao deletar registro: ${err}`);
    }
  }

  setarValoresModal(): void {
    this.title = 'Deletar Registro de Pessoa';
    this.message = `Tem certeza que você deseja realmente excluir este registro?`;
  }

  listNotEmpty(): boolean {
    return this.pessoas !== undefined && this.pessoas.length > 0;
  }

}
