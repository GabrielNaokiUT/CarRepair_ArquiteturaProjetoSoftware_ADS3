import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';

import { validarCpfBasico, validarTelefoneBasico } from '../../core/validacoes/campos.util';
import { Mecanico } from '../../modelos/mecanico';
import { MecanicosService } from '../../services/dominios/mecanicos.service';
import { MensagemService } from '../../shared/mensagens/mensagem.service';

@Component({
  selector: 'app-mecanicos',
  imports: [CommonModule, FormsModule],
  templateUrl: './mecanicos.component.html',
  styleUrl: './mecanicos.component.css'
})
export class MecanicosComponent implements OnInit {
  mecanicos: Mecanico[] = [];
  errosFormulario: string[] = [];
  formAberto = false;

  novoMecanico: Omit<Mecanico, 'id' | 'active'> = {
    nome: '',
    cpf: '',
    email: '',
    especialidade: '',
    telefone: '',
    crea: ''
  };

  constructor(
    private readonly mecanicosService: MecanicosService,
    private readonly mensagemService: MensagemService,
    private readonly cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.carregarMecanicos();
  }

  toggleForm(): void {
    this.formAberto = !this.formAberto;
    if (!this.formAberto) {
      this.errosFormulario = [];
    }
  }

  salvarMecanico(form: NgForm): void {
    this.errosFormulario = this.validarFormulario();

    if (form.invalid || this.errosFormulario.length) {
      this.mensagemService.aviso('Revise os campos obrigatórios antes de salvar o mecânico.');
      return;
    }

    this.mecanicosService.adicionar(this.novoMecanico).subscribe({
      next: () => {
        this.mensagemService.sucesso('Mecânico salvo com sucesso.');
        form.resetForm({ nome: '', cpf: '', email: '', especialidade: '', telefone: '', crea: '' });
        this.formAberto = false;
        this.carregarMecanicos();
      },
      error: (err) => {
        const msg: string = err?.error?.message ?? 'Não foi possível salvar o mecânico no momento.';
        this.mensagemService.erro(msg);
      }
    });
  }

  private carregarMecanicos(): void {
    this.mecanicosService.listar().subscribe({
      next: (mecanicos) => {
        this.mecanicos = mecanicos;
        this.cdr.detectChanges();
      },
      error: () => {
        this.mensagemService.erro('Falha ao carregar mecânicos.');
      }
    });
  }

  private validarFormulario(): string[] {
    const erros: string[] = [];

    if (!this.novoMecanico.nome.trim()) {
      erros.push('Informe o nome do mecânico.');
    }

    if (!validarCpfBasico(this.novoMecanico.cpf)) {
      erros.push('CPF inválido. Verifique os 11 dígitos e os dígitos verificadores.');
    }

    if (!this.novoMecanico.especialidade.trim()) {
      erros.push('Informe a especialidade do mecânico.');
    }

    if (!validarTelefoneBasico(this.novoMecanico.telefone)) {
      erros.push('Informe um telefone válido com DDD.');
    }

    return erros;
  }
}
