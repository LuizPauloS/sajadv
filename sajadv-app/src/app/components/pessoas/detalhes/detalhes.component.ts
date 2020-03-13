import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

import { Pessoa } from 'src/app/model/pessoa';
import { PessoasService } from 'src/app/services/pessoas.service';

@Component({
  selector: 'app-detalhes',
  templateUrl: './detalhes.component.html',
  styleUrls: ['./detalhes.component.css']
})
export class DetalhesComponent implements OnInit {

  pessoa: Pessoa;

  constructor(private location: Location,
              private pessoasService: PessoasService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.getById();
  }

  getById(): void {
    const id = +this.activatedRoute.snapshot.paramMap.get('id');
    this.pessoasService.findById(id).subscribe(response => {
      this.pessoa = response;
    });
  }

  back() {
    this.location.back();
  }

}
