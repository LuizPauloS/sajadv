import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { NovoComponent } from '../components/pessoas/novo/novo.component';
import { ListaComponent } from '../components/pessoas/lista/lista.component';
import { EditarComponent } from '../components/pessoas/editar/editar.component';
import { DetalhesComponent } from '../components/pessoas/detalhes/detalhes.component';

const routes: Routes = [
  { path: '', redirectTo: 'lista', pathMatch: 'full' },
  { path: 'novo', component: NovoComponent },
  { path: 'lista', component: ListaComponent },
  { path: 'editar/:id', component: EditarComponent },
  { path: 'detalhes/:id', component: DetalhesComponent }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class PessoasRoutingModule { }
