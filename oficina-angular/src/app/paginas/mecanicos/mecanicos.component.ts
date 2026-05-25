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
  editando = false;
  idEditando: string | null = null;

  novoMecanico: Omit<Mecanico, 'id' | 'active'> = this.mecanicoVazio();

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
      this.editando = false;
      this.idEditando = null;
      this.novoMecanico = this.mecanicoVazio();
    }
  }

  editarMecanico(mecanico: Mecanico): void {
    this.novoMecanico = {
      nome: mecanico.nome,
      cpf: mecanico.cpf,
      email: mecanico.email,
      especialidade: mecanico.especialidade,
      telefone: mecanico.telefone,
      crea: mecanico.crea
    };
    this.idEditando = mecanico.id;
    this.editando = true;
    this.formAberto = true;
    this.errosFormulario = [];
  }

  excluirMecanico(id: string): void {
    if (!window.confirm('Deseja realmente excluir este mecânico?')) return;
    this.mecanicosService.excluir(id).subscribe({
      next: () => {
        this.mensagemService.sucesso('Mecânico excluído com sucesso.');
        this.carregarMecanicos();
      },
      error: (err) => {
        const msg: string = err?.error?.message ?? 'Não foi possível excluir o mecânico.';
        this.mensagemService.erro(msg);
      }
    });
  }

  salvarMecanico(form: NgForm): void {
    this.errosFormulario = this.validarFormulario();

    if (form.invalid || this.errosFormulario.length) {
      this.mensagemService.aviso('Revise os campos obrigatórios antes de salvar o mecânico.');
      return;
    }

    const operacao$ = this.editando && this.idEditando
      ? this.mecanicosService.atualizar(this.idEditando, { ...this.novoMecanico })
      : this.mecanicosService.adicionar({ ...this.novoMecanico });

    operacao$.subscribe({
      next: () => {
        this.mensagemService.sucesso(this.editando ? 'Mecânico atualizado com sucesso.' : 'Mecânico salvo com sucesso.');
        form.resetForm({ nome: '', cpf: '', email: '', especialidade: '', telefone: '', crea: '' });
        this.formAberto = false;
        this.editando = false;
        this.idEditando = null;
        this.novoMecanico = this.mecanicoVazio();
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

  private mecanicoVazio(): Omit<Mecanico, 'id' | 'active'> {
    return { nome: '', cpf: '', email: '', especialidade: '', telefone: '', crea: '' };
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
