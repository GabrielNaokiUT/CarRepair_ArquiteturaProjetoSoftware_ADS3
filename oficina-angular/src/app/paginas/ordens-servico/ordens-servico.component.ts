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

  novaOrdem: Omit<OrdemServico, 'id' | 'active'> = {
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
    }
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
    return this.servicosService.todos.find((s) => s.id === id)?.nome ?? id;
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

    this.ordensServicoService.adicionar({ ...this.novaOrdem }).subscribe({
      next: () => {
        this.mensagemService.sucesso('Ordem de serviço cadastrada com sucesso.');
        form.resetForm({
          idCliente: '',
          idVeiculo: '',
          idUsuarioResponsavel: '',
          idMecanicoResponsavel: '',
          dataAbertura: new Date().toISOString().slice(0, 10),
          descricaoProblema: ''
        });
        this.novaOrdem.idServicosExecutados = [];
        this.novaOrdem.idPecasAplicadas = [];
        this.formAberto = false;
        this.carregarOrdens();
      },
      error: (err) => {
        const msg: string = err?.error?.message ?? 'Não foi possível cadastrar a ordem de serviço no momento.';
        this.mensagemService.erro(msg);
      }
    });
  }

  nomeCliente(clienteId: string): string {
    return this.clientesService.todos.find((c) => c.id === clienteId)?.nome ?? clienteId;
  }

  nomeMecanico(mecanicoId: string): string {
    return this.mecanicosService.todos.find((m) => m.id === mecanicoId)?.nome ?? mecanicoId;
  }

  dadosVeiculo(veiculoId: string): string {
    const veiculo = this.veiculosService.todos.find((v) => v.id === veiculoId);
    return veiculo ? `${veiculo.placa}` : veiculoId;
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
