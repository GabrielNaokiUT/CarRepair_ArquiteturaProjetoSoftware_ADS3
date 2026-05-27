import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { validarCpfBasico, validarEmailBasico, validarTelefoneBasico } from '../../core/validacoes/campos.util';import { Mecanico } from '../../modelos/mecanico';
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

    capitalizarNomeCompleto(event: Event): void {
      const input = event.target as HTMLInputElement;
      const capitalizado = input.value
        .split(' ')
        .map(palavra => palavra.length > 0 ? palavra.charAt(0).toUpperCase() + palavra.slice(1) : '')
        .join(' ');
      input.value = capitalizado;
      this.novoMecanico.nome = capitalizado;
    }

    aplicarMascaraCpf(event: Event): void {
      const input = event.target as HTMLInputElement;
      let value = input.value.replace(/\D/g, '');
      if (value.length > 11) value = value.slice(0, 11);
      value = value
        .replace(/(\d{3})(\d)/, '$1.$2')
        .replace(/(\d{3})(\d)/, '$1.$2')
        .replace(/(\d{3})(\d{1,2})$/, '$1-$2');
      input.value = value;
      this.novoMecanico.cpf = value;
    }

    aplicarMascaraTelefone(event: Event): void {
      const input = event.target as HTMLInputElement;
      let value = input.value.replace(/\D/g, '');
      if (value.length > 11) value = value.slice(0, 11);
      value = value
        .replace(/(\d{2})(\d)/, '($1) $2')
        .replace(/(\d{5})(\d{1,4})$/, '$1-$2');
      input.value = value;
      this.novoMecanico.telefone = value;
    }

    capitalizarPrimeira(event: Event): void {
      const input = event.target as HTMLInputElement;
      const value = input.value;
      if (!value) return;
      const capitalizado = value.charAt(0).toUpperCase() + value.slice(1);
      input.value = capitalizado;
      this.novoMecanico.especialidade = capitalizado;
    }

    aplicarMascaraCrea(event: Event): void {
        const input = event.target as HTMLInputElement;
        let value = input.value.toUpperCase();

        value = value.replace(/[^A-Z0-9/-]/g, '');

        const letras = value.replace(/[^A-Z]/g, '').slice(0, 2);
        const numeros = value.replace(/[^0-9]/g, '').slice(0, 6);
        const tipo = value.includes('/') ? value.split('/')[1].replace(/[^A-Z]/g, '').slice(0, 1) : '';

        if (numeros.length > 0 && tipo.length > 0) {
          value = `${letras}-${numeros}/${tipo}`;
        } else if (numeros.length > 0) {
          value = `${letras}-${numeros}`;
        } else {
          value = letras;
        }

        input.value = value;
        this.novoMecanico.crea = value;
      }

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

    if (!validarEmailBasico(this.novoMecanico.email)) {
        erros.push('Informe um e-mail válido (ex: nome@dominio.com).');
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
