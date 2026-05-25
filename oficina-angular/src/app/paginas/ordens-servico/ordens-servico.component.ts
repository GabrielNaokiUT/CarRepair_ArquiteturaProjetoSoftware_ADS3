import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { forkJoin } from 'rxjs';

import { Cliente } from '../../modelos/cliente';
import { Mecanico } from '../../modelos/mecanico';
import { OrdemServico } from '../../modelos/ordem-servico';
import { Servico } from '../../modelos/servico';
import { Usuario } from '../../modelos/usuario';
import { Veiculo } from '../../modelos/veiculo';
import { ClientesService } from '../../services/dominios/clientes.service';
import { MecanicosService } from '../../services/dominios/mecanicos.service';
import { OrdensServicoService } from '../../services/dominios/ordens-servico.service';
import { ServicosService } from '../../services/dominios/servicos.service';
import { UsuariosService } from '../../services/dominios/usuarios.service';
import { VeiculosService } from '../../services/dominios/veiculos.service';
import { MensagemService } from '../../shared/mensagens/mensagem.service';

@Component({
  selector: 'app-ordens-servico',
  imports: [CommonModule, FormsModule],
  templateUrl: './ordens-servico.component.html',
  styleUrl: './ordens-servico.component.css'
})
export class OrdensServicoComponent implements OnInit {
  clientes: Cliente[] = [];
  usuarios: Usuario[] = [];
  veiculos: Veiculo[] = [];
  mecanicos: Mecanico[] = [];
  servicos: Servico[] = [];
  ordensServico: OrdemServico[] = [];
  errosFormulario: string[] = [];
  formAberto = false;
  editando = false;
  idEditando: string | null = null;

  novaOrdem: Omit<OrdemServico, 'id' | 'active'> = this.ordemVazia();

  servicoSelecionadoId = '';

  constructor(
    private readonly clientesService: ClientesService,
    private readonly usuariosService: UsuariosService,
    private readonly veiculosService: VeiculosService,
    private readonly mecanicosService: MecanicosService,
    private readonly servicosService: ServicosService,
    private readonly ordensServicoService: OrdensServicoService,
    private readonly mensagemService: MensagemService,
    private readonly cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    forkJoin({
      clientes: this.clientesService.listar(),
      usuarios: this.usuariosService.listar(),
      veiculos: this.veiculosService.listar(),
      mecanicos: this.mecanicosService.listar(),
      servicos: this.servicosService.listar()
    }).subscribe({
      next: ({ clientes, usuarios, veiculos, mecanicos, servicos }) => {
        this.clientes = clientes;
        this.usuarios = usuarios;
        this.veiculos = veiculos;
        this.mecanicos = mecanicos;
        this.servicos = servicos;
        this.cdr.detectChanges();
      },
      error: () => {
        this.mensagemService.erro('Falha ao carregar dados de apoio para ordens de serviço.');
      }
    });

    this.carregarOrdens();
  }

  toggleForm(): void {
    this.formAberto = !this.formAberto;
    if (!this.formAberto) {
      this.errosFormulario = [];
      this.editando = false;
      this.idEditando = null;
      this.novaOrdem = this.ordemVazia();
      this.servicoSelecionadoId = '';
    }
  }

  editarOrdem(ordem: OrdemServico): void {
    this.novaOrdem = {
      idCliente: ordem.idCliente,
      idVeiculo: ordem.idVeiculo,
      idUsuarioResponsavel: ordem.idUsuarioResponsavel,
      idMecanicoResponsavel: ordem.idMecanicoResponsavel,
      dataAbertura: ordem.dataAbertura,
      statusOrdemServico: ordem.statusOrdemServico,
      descricaoProblema: ordem.descricaoProblema,
      idServicosExecutados: [...ordem.idServicosExecutados],
      idPecasAplicadas: [...ordem.idPecasAplicadas]
    };
    this.idEditando = ordem.id;
    this.editando = true;
    this.formAberto = true;
    this.servicoSelecionadoId = '';
    this.errosFormulario = [];
  }

  excluirOrdem(id: string): void {
    if (!window.confirm('Deseja realmente excluir esta ordem de serviço?')) return;
    this.ordensServicoService.excluir(id).subscribe({
      next: () => {
        this.mensagemService.sucesso('Ordem de serviço excluída com sucesso.');
        this.carregarOrdens();
      },
      error: (err) => {
        const msg: string = err?.error?.message ?? 'Não foi possível excluir a ordem de serviço.';
        this.mensagemService.erro(msg);
      }
    });
  }

  adicionarServico(): void {
    if (!this.servicoSelecionadoId) {
      this.mensagemService.aviso('Selecione um serviço antes de adicionar.');
      return;
    }
    if (this.novaOrdem.idServicosExecutados.includes(this.servicoSelecionadoId)) {
      this.mensagemService.aviso('Este serviço já foi adicionado.');
      return;
    }
    this.novaOrdem.idServicosExecutados = [...this.novaOrdem.idServicosExecutados, this.servicoSelecionadoId];
    this.servicoSelecionadoId = '';
  }

  nomeServico(id: string): string {
    return this.servicos.find((s) => s.id === id)?.nome ?? id;
  }

  removerServico(id: string): void {
    this.novaOrdem.idServicosExecutados = this.novaOrdem.idServicosExecutados.filter((s) => s !== id);
  }

  salvarOrdemServico(form: NgForm): void {
    this.errosFormulario = this.validarFormulario();

    if (form.invalid || this.errosFormulario.length) {
      this.mensagemService.aviso('Revise os campos obrigatórios antes de cadastrar a ordem de serviço.');
      return;
    }

    const operacao$ = this.editando && this.idEditando
      ? this.ordensServicoService.atualizar(this.idEditando, { ...this.novaOrdem })
      : this.ordensServicoService.adicionar({ ...this.novaOrdem });

    operacao$.subscribe({
      next: () => {
        this.mensagemService.sucesso(this.editando ? 'Ordem de serviço atualizada com sucesso.' : 'Ordem de serviço cadastrada com sucesso.');
        form.resetForm({
          idCliente: '',
          idVeiculo: '',
          idUsuarioResponsavel: '',
          idMecanicoResponsavel: '',
          dataAbertura: new Date().toISOString().slice(0, 10),
          descricaoProblema: ''
        });
        this.novaOrdem = this.ordemVazia();
        this.formAberto = false;
        this.editando = false;
        this.idEditando = null;
        this.servicoSelecionadoId = '';
        this.carregarOrdens();
      },
      error: (err) => {
        const msg: string = err?.error?.message ?? 'Não foi possível salvar a ordem de serviço no momento.';
        this.mensagemService.erro(msg);
      }
    });
  }

  nomeCliente(clienteId: string): string {
    return this.clientes.find((c) => c.id === clienteId)?.nome ?? clienteId;
  }

  nomeMecanico(mecanicoId: string): string {
    return this.mecanicos.find((m) => m.id === mecanicoId)?.nome ?? mecanicoId;
  }

  dadosVeiculo(veiculoId: string): string {
    const veiculo = this.veiculos.find((v) => v.id === veiculoId);
    return veiculo ? veiculo.placa : veiculoId;
  }

  badgeClassStatus(status: string): Record<string, boolean> {
    return {
      'badge': true,
      'b-aberta':  status === 'aberta',
      'b-exec':    status === 'em_execucao',
      'b-fin':     status === 'finalizada',
      'b-cancel':  status === 'cancelada'
    };
  }

  labelStatus(status: string): string {
    const map: Record<string, string> = {
      aberta: 'Aberta',
      em_execucao: 'Em execução',
      finalizada: 'Finalizada',
      cancelada: 'Cancelada'
    };
    return map[status] ?? status;
  }

  private ordemVazia(): Omit<OrdemServico, 'id' | 'active'> {
    return {
      idCliente: '',
      idVeiculo: '',
      idUsuarioResponsavel: '',
      idMecanicoResponsavel: '',
      dataAbertura: new Date().toISOString().slice(0, 10),
      statusOrdemServico: 'aberta',
      descricaoProblema: '',
      idServicosExecutados: [],
      idPecasAplicadas: []
    };
  }

  private carregarOrdens(): void {
    this.ordensServicoService.listar().subscribe({
      next: (ordensServico) => {
        this.ordensServico = ordensServico;
        this.cdr.detectChanges();
      },
      error: () => {
        this.mensagemService.erro('Falha ao carregar ordens de serviço.');
      }
    });
  }

  private validarFormulario(): string[] {
    const erros: string[] = [];

    if (!this.novaOrdem.idCliente) erros.push('Selecione um cliente.');
    if (!this.novaOrdem.idVeiculo) erros.push('Selecione um veículo.');
    if (!this.novaOrdem.idUsuarioResponsavel) erros.push('Selecione um usuário responsável.');
    if (!this.novaOrdem.idMecanicoResponsavel) erros.push('Selecione um mecânico responsável.');
    if (!this.novaOrdem.dataAbertura) erros.push('Informe a data de abertura.');
    if (this.novaOrdem.descricaoProblema.trim().length < 5) erros.push('Descreva o problema com ao menos 5 caracteres.');

    return erros;
  }
}
