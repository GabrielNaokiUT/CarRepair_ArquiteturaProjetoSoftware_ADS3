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

  novoCliente: Omit<Cliente, 'id' | 'active'> = {
    nome: '',
    cpf: '',
    email: '',
    telefone: ''
  };

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
    }
  }

  salvarCliente(form: NgForm): void {
    this.errosFormulario = this.validarFormulario();

    if (form.invalid || this.errosFormulario.length) {
      this.mensagemService.aviso('Revise os campos obrigatórios antes de salvar o cliente.');
      return;
    }

    this.clientesService.adicionar(this.novoCliente).subscribe({
      next: () => {
        this.mensagemService.sucesso('Cliente salvo com sucesso.');
        form.resetForm({ nome: '', cpf: '', email: '', telefone: '' });
        this.formAberto = false;
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
