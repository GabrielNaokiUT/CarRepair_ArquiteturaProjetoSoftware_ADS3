import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
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

  novoUsuario: Omit<Usuario, 'id'> = {
    nome: '',
    login: '',
    email: '',
    perfil: 'atendente'
  };

  constructor(
    private readonly usuariosService: UsuariosService,
    private readonly mensagemService: MensagemService
  ) {}

  ngOnInit(): void {
    this.carregarUsuarios();
  }

  salvarUsuario(form: NgForm): void {
    this.errosFormulario = this.validarFormulario();

    if (form.invalid || this.errosFormulario.length) {
      this.mensagemService.aviso('Revise os campos obrigatorios antes de salvar o usuario.');
      return;
    }

    this.usuariosService.adicionar(this.novoUsuario).subscribe({
      next: () => {
        this.mensagemService.sucesso('Usuario salvo com sucesso.');
        form.resetForm({ nome: '', login: '', email: '', perfil: 'atendente' });
        this.carregarUsuarios();
      },
      error: () => {
        this.mensagemService.erro('Nao foi possivel salvar o usuario no momento.');
      }
    });
  }

  private carregarUsuarios(): void {
    this.usuariosService.listar().subscribe({
      next: (usuarios) => {
        this.usuarios = usuarios;
      },
      error: () => {
        this.mensagemService.erro('Falha ao carregar usuarios.');
      }
    });
  }

  private validarFormulario(): string[] {
    const erros: string[] = [];

    if (!this.novoUsuario.nome.trim()) {
      erros.push('Informe o nome do usuario.');
    }

    if (!this.novoUsuario.login.trim()) {
      erros.push('Informe o login do usuario.');
    }

    if (!this.novoUsuario.email.trim() || !this.novoUsuario.email.includes('@')) {
      erros.push('Informe um e-mail valido.');
    }

    if (!this.novoUsuario.perfil) {
      erros.push('Selecione o perfil do usuario.');
    }

    return erros;
  }
}
