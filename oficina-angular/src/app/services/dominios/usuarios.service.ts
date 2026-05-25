import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { ApiBaseService } from '../../core/http/api-base.service';
import { Usuario } from '../../modelos/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService extends ApiBaseService {
  private readonly endpoint = 'usuarios';
  private usuarios: Usuario[] = [];

  constructor(http: HttpClient) {
    super(http);
  }

  listar(): Observable<Usuario[]> {
    return this.get<any>(`${this.endpoint}/todos`).pipe(
      map((response) => (response?.content ?? response) as Usuario[]),
      tap((usuarios) => { this.usuarios = [...usuarios]; }),
      catchError((err) => throwError(() => err))
    );
  }

  get todos(): Usuario[] { return this.usuarios; }

  adicionar(usuario: Omit<Usuario, 'id' | 'active'>): Observable<Usuario> {
    return this.post<Usuario, Omit<Usuario, 'id' | 'active'>>(this.endpoint, usuario).pipe(
      tap((usuarioCriado) => {
        this.usuarios = [...this.usuarios, usuarioCriado];
      }),
      catchError((err) => throwError(() => err))
    );
  }
}
