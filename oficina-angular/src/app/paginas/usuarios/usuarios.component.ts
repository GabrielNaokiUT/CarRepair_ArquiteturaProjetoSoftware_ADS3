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

  novoUsuario: Omit<Usuario, 'id' | 'active'> = {
    nome: '',
    login: '',
    email: '',
    perfil: 'atendente'
  };

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
    }
  }

  salvarUsuario(form: NgForm): void {
    this.errosFormulario = this.validarFormulario();

    if (form.invalid || this.errosFormulario.length) {
      this.mensagemService.aviso('Revise os campos obrigatórios antes de salvar o usuário.');
      return;
    }

    this.usuariosService.adicionar(this.novoUsuario).subscribe({
      next: () => {
        this.mensagemService.sucesso('Usuário salvo com sucesso.');
        form.resetForm({ nome: '', login: '', email: '', perfil: 'atendente' });
        this.formAberto = false;
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
