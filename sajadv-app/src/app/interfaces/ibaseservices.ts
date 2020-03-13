import { Observable } from 'rxjs';

export interface IBaseServices<T> {

  save(t: T): Observable<T>;

  update(t: T): Observable<T>;

  findAll(): Observable<T[]>;

  findById(id: number): Observable<T>;

  delete(id: number): Observable<any>;
}
