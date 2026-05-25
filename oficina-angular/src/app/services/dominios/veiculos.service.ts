import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { ApiBaseService } from '../../core/http/api-base.service';
import { Veiculo } from '../../modelos/veiculo';
@Injectable({
  providedIn: 'root'
})
export class VeiculosService extends ApiBaseService {
  private readonly endpoint = 'veiculos';

   private veiculos: Veiculo[] = [];


  constructor(http: HttpClient) {
    super(http);
  }

  listar(): Observable<Veiculo[]> {
    return this.get<any>(`${this.endpoint}/todos`).pipe(
      map((response) => (response?.content ?? response) as Veiculo[]),
      tap((veiculos) => { this.veiculos = [...veiculos]; }),
      catchError((err) => throwError(() => err))
    );
  }

  get todos(): Veiculo[] { return this.veiculos; }

  adicionar(veiculo: Omit<Veiculo, 'id' | 'active'>): Observable<Veiculo> {
    return this.post<Veiculo, Omit<Veiculo, 'id' | 'active'>>(this.endpoint, veiculo).pipe(
      tap((veiculoCriado) => {
        this.veiculos = [...this.veiculos, veiculoCriado];
      }),
      catchError((err) => throwError(() => err))
    );
  }

}
