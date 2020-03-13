import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ToastrService } from 'ngx-toastr';

import { Pessoa } from 'src/app/model/pessoa';
import { PessoasService } from 'src/app/services/pessoas.service';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.css']
})
export class EditarComponent implements OnInit {
  pessoa: Pessoa;
  formUpdate: FormGroup;

  constructor(private router: Router,
              private toastr: ToastrService,
              private pessoasService: PessoasService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.initFormUpdate();
    this.getLoadById();
  }

  initFormUpdate(): void {
    this.formUpdate = new FormGroup({
      id: new FormControl('', [Validators.required]),
      nome: new FormControl('', [Validators.required]),
      cpf: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required]),
      dataNascimento: new FormControl('', [Validators.required]),
    });
  }

  getLoadById(): void {
    try {
      const id = +this.activatedRoute.snapshot.paramMap.get('id');
      this.pessoasService.findById(id).subscribe(response => {
        this.pessoa = response;
        this.updateForm(this.pessoa);
      }, (error: Error) => {
        console.log(error);
        this.toastr.error(`Ocorreu um erro ao buscar dados de ${this.pessoa.nome}: ${error.message}`);
      });
    } catch (err) {
      console.log(err);
      this.toastr.error(`Ocorreu um erro inesperado ao buscar dados de ${this.pessoa.nome}: ${err}`);
    }
  }

  updateForm(pessoa: Pessoa): void {
    this.formUpdate.patchValue({
      id: pessoa.id,
      nome: pessoa.nome,
      cpf: pessoa.cpf,
      email: pessoa.email,
      dataNascimento: pessoa.dataNascimento
    });
  }

  update(): void {
    try {
      if (this.formUpdate !== undefined && this.formUpdate.valid) {
        this.pessoasService.update(this.formUpdate.value).subscribe(response => {
          this.updateForm(response);
          this.toastr.success('Registro atualizado com sucesso!');
          this.router.navigate(['/pessoas/lista']);
        });
      } else {
        this.toastr.warning('Formulário incompleto! Verifique os dados e tente novamente.');
      }
    } catch (err) {
      console.log(err);
      this.toastr.error('Ocorreu um erro inesperado ao atualizar registro: ' + err);
    }
  }

}
