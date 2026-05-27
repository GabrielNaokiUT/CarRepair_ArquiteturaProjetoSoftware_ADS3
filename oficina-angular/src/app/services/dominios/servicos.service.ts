import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { ApiBaseService } from '../../core/http/api-base.service';
import { Servico } from '../../modelos/servico';

@Injectable({
  providedIn: 'root'
})
export class ServicosService extends ApiBaseService {
  private readonly endpoint = 'servicos';
  private servicos: Servico[] = [];

  constructor(http: HttpClient) {
    super(http);
  }

  get todos(): Servico[] { return this.servicos; }

  listar(): Observable<Servico[]> {
    return this.get<any>(`${this.endpoint}/todos`).pipe(
      map((response) => (response?.content ?? response) as Servico[]),
      tap((servicos) => { this.servicos = [...servicos]; }),
      catchError((err) => throwError(() => err))
    );
  }

  adicionar(servico: Omit<Servico, 'id' | 'active'>): Observable<Servico> {
    return this.post<Servico, Omit<Servico, 'id' | 'active'>>(this.endpoint, servico).pipe(
      tap((servicoCriado) => { this.servicos = [...this.servicos, servicoCriado]; }),
      catchError((err) => throwError(() => err))
    );
  }

  atualizar(id: string, servico: Omit<Servico, 'id' | 'active'>): Observable<Servico> {
    return this.put<Servico, Omit<Servico, 'id' | 'active'>>(this.endpoint, id, servico).pipe(
      tap((atualizado) => { this.servicos = this.servicos.map(s => s.id === id ? atualizado : s); }),
      catchError((err) => throwError(() => err))
    );
  }

  excluir(id: string): Observable<void> {
    return this.delete(this.endpoint, id).pipe(
      tap(() => { this.servicos = this.servicos.filter(s => s.id !== id); }),
      catchError((err) => throwError(() => err))
    );
  }
}
