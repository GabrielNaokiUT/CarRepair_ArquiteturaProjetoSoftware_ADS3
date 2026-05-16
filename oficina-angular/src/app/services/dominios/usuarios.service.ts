import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
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
    return this.get<Usuario[]>(${this.endpoint}/todos).pipe(
      tap((usuarios) => {
        this.usuarios = [...usuarios];
      }),
      catchError((err) => throwError(() => err))
    );
  }

  adicionar(usuario: Omit<Usuario, 'id'>): Observable<Usuario> {
    return this.post<Usuario, Omit<Usuario, 'id'>>(this.endpoint, usuario).pipe(
      tap((usuarioCriado) => {
        this.usuarios = [...this.usuarios, usuarioCriado];
      }),
      catchError((err) => throwError(() => err))
    );
  }
}
