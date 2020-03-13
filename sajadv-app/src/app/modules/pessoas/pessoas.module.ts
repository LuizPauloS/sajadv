import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { NgxPaginationModule } from 'ngx-pagination';

import { SharedModule } from '../shared/shared.module';
import { PessoasService } from '../../services/pessoas.service';
import { PessoasRoutingModule } from 'src/app/routes/pessoas.routing';
import { NovoComponent } from '../../components/pessoas/novo/novo.component';
import { ListaComponent } from '../../components/pessoas/lista/lista.component';
import { EditarComponent } from '../../components/pessoas/editar/editar.component';
import { DetalhesComponent } from '../../components/pessoas/detalhes/detalhes.component';

@NgModule({
  declarations: [
    NovoComponent,
    ListaComponent,
    EditarComponent,
    DetalhesComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    HttpClientModule,
    ReactiveFormsModule,
    PessoasRoutingModule,
    NgxPaginationModule
  ],
  providers: [
    PessoasService
  ]
})
export class PessoasModule { }
