import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

import { Pessoa } from '../model/pessoa';
import { HttpClient } from '@angular/common/http';
import { IBaseServices } from '../interfaces/ibaseservices';
import { environment } from './../../environments/environment';

@Injectable()
export class PessoasService implements IBaseServices<Pessoa> {

  constructor(private httpClient: HttpClient) { }

  findAll(): Observable<any> {
    return this.httpClient.get<any>(environment.pessoas.findAll);
  }

  findById(id: number): Observable<Pessoa> {
    return this.httpClient.get<Pessoa>(`${environment.pessoas.findById}/${id}`);
  }

  save(pessoa: Pessoa): Observable<Pessoa> {
    return this.httpClient.post<Pessoa>(environment.pessoas.save, pessoa);
  }

  update(pessoa: Pessoa): Observable<Pessoa> {
    return this.httpClient.put<Pessoa>(`${environment.pessoas.update}/${pessoa.id}`, pessoa);
  }

  delete(id: number): Observable<any> {
    return this.httpClient.delete<any>(`${environment.pessoas.delete}/${id}`);
  }

}
