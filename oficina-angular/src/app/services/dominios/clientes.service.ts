import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { ApiBaseService } from '../../core/http/api-base.service';
import { Cliente } from '../../modelos/cliente';
@Injectable({
  providedIn: 'root'
})
export class ClientesService extends ApiBaseService {
  private readonly endpoint = 'clientes';

  private clientes: Cliente[] = [];


  constructor(http: HttpClient) {
    super(http);
  }

  listar(): Observable<Cliente[]> {
    return this.get<any>(`${this.endpoint}/todos`).pipe(
      map((response) => (response?.content ?? response) as Cliente[]),
      tap((clientes) => { this.clientes = [...clientes]; }),
      catchError((err) => throwError(() => err))
    );
  }

  get todos(): Cliente[] { return this.clientes; }

  adicionar(cliente: Omit<Cliente, 'id' | 'active'>): Observable<Cliente> {
    return this.post<Cliente, Omit<Cliente, 'id' | 'active'>>(this.endpoint, cliente).pipe(
      tap((clienteCriado) => {
        this.clientes = [...this.clientes, clienteCriado];
      }),
      catchError((err) => throwError(() => err))
    );
  }

  atualizar(id: string, cliente: Omit<Cliente, 'id' | 'active'>): Observable<Cliente> {
    return this.put<Cliente, Omit<Cliente, 'id' | 'active'>>(this.endpoint, id, cliente).pipe(
      tap((atualizado) => { this.clientes = this.clientes.map(c => c.id === id ? atualizado : c); }),
      catchError((err) => throwError(() => err))
    );
  }

  excluir(id: string): Observable<void> {
    return this.delete(this.endpoint, id).pipe(
      tap(() => { this.clientes = this.clientes.filter(c => c.id !== id); }),
      catchError((err) => throwError(() => err))
    );
  }
}
