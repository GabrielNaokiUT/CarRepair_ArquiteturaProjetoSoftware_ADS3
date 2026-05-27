import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';

import { Usuario } from '../../modelos/usuario';
import { UsuariosService } from '../../services/dominios/usuarios.service';
import { MensagemService } from '../../shared/mensagens/mensagem.service';

@Component({
  selector: 'app-usuarios',
  imports: [CommonModule, FormsModule],
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.css'
})
export class UsuariosComponent implements OnInit {
  usuarios: Usuario[] = [];
  errosFormulario: string[] = [];
  formAberto = false;
  editando = false;
  idEditando: string | null = null;

  novoUsuario: Omit<Usuario, 'id' | 'active'> = this.usuarioVazio();

  constructor(
    private readonly usuariosService: UsuariosService,
    private readonly mensagemService: MensagemService,
    private readonly cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.carregarUsuarios();
  }

  toggleForm(): void {
    this.formAberto = !this.formAberto;
    if (!this.formAberto) {
      this.errosFormulario = [];
      this.editando = false;
      this.idEditando = null;
      this.novoUsuario = this.usuarioVazio();
    }
  }

  editarUsuario(usuario: Usuario): void {
    this.novoUsuario = { nome: usuario.nome, login: usuario.login, email: usuario.email, perfil: usuario.perfil };
    this.idEditando = usuario.id;
    this.editando = true;
    this.formAberto = true;
    this.errosFormulario = [];
  }

  excluirUsuario(id: string): void {
    if (!window.confirm('Deseja realmente excluir este usuário?')) return;
    this.usuariosService.excluir(id).subscribe({
      next: () => {
        this.mensagemService.sucesso('Usuário excluído com sucesso.');
        this.carregarUsuarios();
      },
      error: (err) => {
        const msg: string = err?.error?.message ?? 'Não foi possível excluir o usuário.';
        this.mensagemService.erro(msg);
      }
    });
  }

  salvarUsuario(form: NgForm): void {
    this.errosFormulario = this.validarFormulario();

    if (form.invalid || this.errosFormulario.length) {
      this.mensagemService.aviso('Revise os campos obrigatórios antes de salvar o usuário.');
      return;
    }

    const operacao$ = this.editando && this.idEditando
      ? this.usuariosService.atualizar(this.idEditando, { ...this.novoUsuario })
      : this.usuariosService.adicionar({ ...this.novoUsuario });

    operacao$.subscribe({
      next: () => {
        this.mensagemService.sucesso(this.editando ? 'Usuário atualizado com sucesso.' : 'Usuário salvo com sucesso.');
        form.resetForm({ nome: '', login: '', email: '', perfil: 'atendente' });
        this.formAberto = false;
        this.editando = false;
        this.idEditando = null;
        this.novoUsuario = this.usuarioVazio();
        this.carregarUsuarios();
      },
      error: (err) => {
        const msg: string = err?.error?.message ?? 'Não foi possível salvar o usuário no momento.';
        this.mensagemService.erro(msg);
      }
    });
  }

  private carregarUsuarios(): void {
    this.usuariosService.listar().subscribe({
      next: (usuarios) => {
        this.usuarios = usuarios;
        this.cdr.detectChanges();
      },
      error: () => {
        this.mensagemService.erro('Falha ao carregar usuários.');
      }
    });
  }

  private usuarioVazio(): Omit<Usuario, 'id' | 'active'> {
    return { nome: '', login: '', email: '', perfil: 'atendente' };
  }

  private validarFormulario(): string[] {
    const erros: string[] = [];

    if (!this.novoUsuario.nome.trim()) {
      erros.push('Informe o nome do usuário.');
    }

    if (!this.novoUsuario.login.trim()) {
      erros.push('Informe o login do usuário.');
    }

    if (!this.novoUsuario.email.trim() || !this.novoUsuario.email.includes('@')) {
      erros.push('Informe um e-mail válido.');
    }

    if (!this.novoUsuario.perfil) {
      erros.push('Selecione o perfil do usuário.');
    }

    return erros;
  }
}
