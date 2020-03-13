import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'sajadv', pathMatch: 'full' },
  { path: 'sajadv', loadChildren: () => import('./modules/dashboard/dashboard.module').then(m => m.DashboardModule) },
  { path: '', children: [
    { path: 'pessoas', loadChildren: () => import('./modules/pessoas/pessoas.module').then(m => m.PessoasModule) }
  ]}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
