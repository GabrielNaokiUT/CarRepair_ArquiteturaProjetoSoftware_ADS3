import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { ApiBaseService } from '../../core/http/api-base.service';
import { OrdemServico } from '../../modelos/ordem-servico';

@Injectable({
  providedIn: 'root'
})
export class OrdensServicoService extends ApiBaseService {
  private readonly endpoint = 'ordens-servico';

  private ordens: OrdemServico[] = [];

  constructor(http: HttpClient) {
    super(http);
  }

  listar(): Observable<OrdemServico[]> {
    return this.get<any>(`${this.endpoint}/todos`).pipe(
      map((response) => (response?.content ?? response) as OrdemServico[]),
      tap((ordens) => { this.ordens = [...ordens]; }),
      catchError((err) => throwError(() => err))
    );
  }

  adicionar(ordem: Omit<OrdemServico, 'id' | 'active'>): Observable<OrdemServico> {
    return this.post<OrdemServico, Omit<OrdemServico, 'id' | 'active'>>(this.endpoint, ordem).pipe(
      tap((ordemCriada) => { this.ordens = [...this.ordens, ordemCriada]; }),
      catchError((err) => throwError(() => err))
    );
  }

  atualizar(id: string, ordem: Omit<OrdemServico, 'id' | 'active'>): Observable<OrdemServico> {
    return this.put<OrdemServico, Omit<OrdemServico, 'id' | 'active'>>(this.endpoint, id, ordem).pipe(
      tap((atualizado) => { this.ordens = this.ordens.map(o => o.id === id ? atualizado : o); }),
      catchError((err) => throwError(() => err))
    );
  }

  excluir(id: string): Observable<void> {
    return this.delete(this.endpoint, id).pipe(
      tap(() => { this.ordens = this.ordens.filter(o => o.id !== id); }),
      catchError((err) => throwError(() => err))
    );
  }
}
