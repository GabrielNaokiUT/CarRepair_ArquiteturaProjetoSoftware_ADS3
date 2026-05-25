import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';

import { validarCpfBasico, validarTelefoneBasico } from '../../core/validacoes/campos.util';
import { Cliente } from '../../modelos/cliente';
import { ClientesService } from '../../services/dominios/clientes.service';
import { MensagemService } from '../../shared/mensagens/mensagem.service';

@Component({
  selector: 'app-clientes',
  imports: [CommonModule, FormsModule],
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.css'
})
export class ClientesComponent implements OnInit {
  clientes: Cliente[] = [];
  errosFormulario: string[] = [];
  formAberto = false;
  editando = false;
  idEditando: string | null = null;

  novoCliente: Omit<Cliente, 'id' | 'active'> = this.clienteVazio();

  constructor(
    private readonly clientesService: ClientesService,
    private readonly mensagemService: MensagemService,
    private readonly cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.carregarClientes();
  }

  toggleForm(): void {
    this.formAberto = !this.formAberto;
    if (!this.formAberto) {
      this.errosFormulario = [];
      this.editando = false;
      this.idEditando = null;
      this.novoCliente = this.clienteVazio();
    }
  }

  editarCliente(cliente: Cliente): void {
    this.novoCliente = { nome: cliente.nome, cpf: cliente.cpf, email: cliente.email, telefone: cliente.telefone };
    this.idEditando = cliente.id;
    this.editando = true;
    this.formAberto = true;
    this.errosFormulario = [];
  }

  excluirCliente(id: string): void {
    if (!window.confirm('Deseja realmente excluir este cliente?')) return;
    this.clientesService.excluir(id).subscribe({
      next: () => {
        this.mensagemService.sucesso('Cliente excluído com sucesso.');
        this.carregarClientes();
      },
      error: (err) => {
        const msg: string = err?.error?.message ?? 'Não foi possível excluir o cliente.';
        this.mensagemService.erro(msg);
      }
    });
  }

  salvarCliente(form: NgForm): void {
    this.errosFormulario = this.validarFormulario();

    if (form.invalid || this.errosFormulario.length) {
      this.mensagemService.aviso('Revise os campos obrigatórios antes de salvar o cliente.');
      return;
    }

    const operacao$ = this.editando && this.idEditando
      ? this.clientesService.atualizar(this.idEditando, { ...this.novoCliente })
      : this.clientesService.adicionar({ ...this.novoCliente });

    operacao$.subscribe({
      next: () => {
        this.mensagemService.sucesso(this.editando ? 'Cliente atualizado com sucesso.' : 'Cliente cadastrado com sucesso.');
        form.resetForm({ nome: '', cpf: '', email: '', telefone: '' });
        this.formAberto = false;
        this.editando = false;
        this.idEditando = null;
        this.novoCliente = this.clienteVazio();
        this.carregarClientes();
      },
      error: (err) => {
        const msg: string = err?.error?.message ?? 'Não foi possível salvar o cliente no momento.';
        this.mensagemService.erro(msg);
      }
    });
  }

  private carregarClientes(): void {
    this.clientesService.listar().subscribe({
      next: (clientes) => {
        this.clientes = clientes;
        this.cdr.detectChanges();
      },
      error: () => {
        this.mensagemService.erro('Falha ao carregar clientes.');
      }
    });
  }

  private clienteVazio(): Omit<Cliente, 'id' | 'active'> {
    return { nome: '', cpf: '', email: '', telefone: '' };
  }

  private validarFormulario(): string[] {
    const erros: string[] = [];

    if (!this.novoCliente.nome.trim()) {
      erros.push('Informe o nome do cliente.');
    }

    if (!validarCpfBasico(this.novoCliente.cpf)) {
      erros.push('CPF inválido. Verifique os 11 dígitos e os dígitos verificadores.');
    }

    if (!validarTelefoneBasico(this.novoCliente.telefone)) {
      erros.push('Informe um telefone válido com DDD.');
    }

    return erros;
  }
}
