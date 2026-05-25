import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';

import { Servico } from '../../modelos/servico';
import { ServicosService } from '../../services/dominios/servicos.service';
import { MensagemService } from '../../shared/mensagens/mensagem.service';

@Component({
  selector: 'app-servicos',
  imports: [CommonModule, FormsModule],
  templateUrl: './servicos.component.html',
  styleUrl: './servicos.component.css'
})
export class ServicosComponent implements OnInit {
  servicos: Servico[] = [];
  errosFormulario: string[] = [];
  formAberto = false;
  editando = false;
  idEditando: string | null = null;

  novoServico: Omit<Servico, 'id' | 'active'> = this.servicoVazio();

  constructor(
    private readonly servicosService: ServicosService,
    private readonly mensagemService: MensagemService,
    private readonly cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.carregarServicos();
  }

  toggleForm(): void {
    this.formAberto = !this.formAberto;
    if (!this.formAberto) {
      this.errosFormulario = [];
      this.editando = false;
      this.idEditando = null;
      this.novoServico = this.servicoVazio();
    }
  }

  editarServico(servico: Servico): void {
    this.novoServico = { nome: servico.nome, descricao: servico.descricao, preco: servico.preco };
    this.idEditando = servico.id;
    this.editando = true;
    this.formAberto = true;
    this.errosFormulario = [];
  }

  excluirServico(id: string): void {
    if (!window.confirm('Deseja realmente excluir este serviço?')) return;
    this.servicosService.excluir(id).subscribe({
      next: () => {
        this.mensagemService.sucesso('Serviço excluído com sucesso.');
        this.carregarServicos();
      },
      error: (err) => {
        const msg: string = err?.error?.message ?? 'Não foi possível excluir o serviço.';
        this.mensagemService.erro(msg);
      }
    });
  }

  salvarServico(form: NgForm): void {
    this.errosFormulario = this.validarFormulario();

    if (form.invalid || this.errosFormulario.length) {
      this.mensagemService.aviso('Revise os campos obrigatórios antes de salvar o serviço.');
      return;
    }

    const operacao$ = this.editando && this.idEditando
      ? this.servicosService.atualizar(this.idEditando, { ...this.novoServico })
      : this.servicosService.adicionar({ ...this.novoServico });

    operacao$.subscribe({
      next: () => {
        this.mensagemService.sucesso(this.editando ? 'Serviço atualizado com sucesso.' : 'Serviço salvo com sucesso.');
        form.resetForm({ nome: '', descricao: '', preco: 0 });
        this.formAberto = false;
        this.editando = false;
        this.idEditando = null;
        this.novoServico = this.servicoVazio();
        this.carregarServicos();
      },
      error: (err) => {
        const msg: string = err?.error?.message ?? 'Não foi possível salvar o serviço no momento.';
        this.mensagemService.erro(msg);
      }
    });
  }

  private carregarServicos(): void {
    this.servicosService.listar().subscribe({
      next: (servicos) => {
        this.servicos = servicos;
        this.cdr.detectChanges();
      },
      error: () => {
        this.mensagemService.erro('Falha ao carregar serviços.');
      }
    });
  }

  private servicoVazio(): Omit<Servico, 'id' | 'active'> {
    return { nome: '', descricao: '', preco: 0 };
  }

  private validarFormulario(): string[] {
    const erros: string[] = [];

    if (!this.novoServico.nome.trim()) {
      erros.push('Informe o nome do serviço.');
    }

    if (!this.novoServico.descricao.trim()) {
      erros.push('Informe a descrição do serviço.');
    }

    if (!this.novoServico.preco || this.novoServico.preco <= 0) {
      erros.push('Informe um valor maior que zero.');
    }

    return erros;
  }
}
