import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ToastrService } from 'ngx-toastr';

import { PessoasService } from 'src/app/services/pessoas.service';

@Component({
  selector: 'app-novo',
  templateUrl: './novo.component.html',
  styleUrls: ['./novo.component.css']
})
export class NovoComponent implements OnInit {

  formSave: FormGroup;

  constructor(private router: Router,
              private toastr: ToastrService,
              private pessoasService: PessoasService) { }

  ngOnInit() {
    this.initFormSave();
  }

  initFormSave(): void {
    this.formSave = new FormGroup({
      nome: new FormControl('', [Validators.required]),
      cpf: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required]),
      dataNascimento: new FormControl('', [Validators.required])
    });
  }

  cadastrar(): void {
    try {
      if (this.formSave && this.formSave.valid) {
        this.pessoasService.save(this.formSave.value).subscribe(response => {
          this.toastr.success(`Registro de ${response.nome} adicionado com sucesso!`);
          this.router.navigate(['pessoas/lista']);
        }, (error: Error) => {
          console.log(error);
          this.toastr.error(`Ocorreu um erro inesperado ao registrar os dados de ${this.formSave.get('nome').value}:
            ${error.message}`);
        });
      } else {
        this.toastr.warning('Formulário incompleto! Verifique os dados e tente novamente.');
      }
    } catch (err) {
      console.log(err);
      this.toastr.error('Ocorreu um erro inesperado ao registrar pessoa: ' + err);
    }
  }

}
