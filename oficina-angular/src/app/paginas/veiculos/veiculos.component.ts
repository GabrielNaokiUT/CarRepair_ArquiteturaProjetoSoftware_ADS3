import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';

import { validarPlacaBasica } from '../../core/validacoes/campos.util';
import { Cliente } from '../../modelos/cliente';
import { Veiculo } from '../../modelos/veiculo';
import { ClientesService } from '../../services/dominios/clientes.service';
import { VeiculosService } from '../../services/dominios/veiculos.service';
import { MensagemService } from '../../shared/mensagens/mensagem.service';

@Component({
  selector: 'app-veiculos',
  imports: [CommonModule, FormsModule],
  templateUrl: './veiculos.component.html',
  styleUrl: './veiculos.component.css'
})
export class VeiculosComponent implements OnInit {
  clientes: Cliente[] = [];
  veiculos: Veiculo[] = [];
  errosFormulario: string[] = [];
  formAberto = false;
  editando = false;
  idEditando: string | null = null;

  novoVeiculo: Omit<Veiculo, 'id' | 'active'> = this.criarVeiculoVazio();

    aplicarMascaraPlaca(event: Event): void {
        const input = event.target as HTMLInputElement;
        let value = input.value.toUpperCase().replace(/[^A-Z0-9]/g, '');
        if (value.length > 7) value = value.slice(0, 7);

        const mercosul = /^[A-Z]{3}\d[A-Z]/.test(value);
        if (!mercosul && value.length === 7) {
            value = value.slice(0, 3) + '-' + value.slice(3);
        }

        input.value = value;
        this.novoVeiculo.placa = value;
    }

    capitalizarPrimeira(event: Event, campo: 'marca' | 'modelo' | 'cor'): void {
        const input = event.target as HTMLInputElement;
        const value = input.value;
        if (!value) return;
        const capitalizado = value.charAt(0).toUpperCase() + value.slice(1);
        input.value = capitalizado;
        this.novoVeiculo[campo] = capitalizado;
    }
    
    limitarAno(event: Event): void {
        const input = event.target as HTMLInputElement;
        let value = input.value.replace(/[^0-9]/g, '');
        if (value.length > 4) value = value.slice(0, 4);
        input.value = value;
        this.novoVeiculo.anoFabricacao = Number(value);
    }

  constructor(
    private readonly clientesService: ClientesService,
    private readonly veiculosService: VeiculosService,
    private readonly mensagemService: MensagemService,
    private readonly cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.carregarClientes();
    this.carregarVeiculos();
  }

  toggleForm(): void {
    this.formAberto = !this.formAberto;
    if (!this.formAberto) {
      this.errosFormulario = [];
      this.editando = false;
      this.idEditando = null;
      this.novoVeiculo = this.criarVeiculoVazio();
    }
  }

  editarVeiculo(veiculo: Veiculo): void {
    this.novoVeiculo = {
      idCliente: veiculo.idCliente,
      placa: veiculo.placa,
      modelo: veiculo.modelo,
      marca: veiculo.marca,
      anoFabricacao: veiculo.anoFabricacao,
      cor: veiculo.cor,
      quilometragem: veiculo.quilometragem
    };
    this.idEditando = veiculo.id;
    this.editando = true;
    this.formAberto = true;
    this.errosFormulario = [];
  }

  excluirVeiculo(id: string): void {
    if (!window.confirm('Deseja realmente excluir este veículo?')) return;
    this.veiculosService.excluir(id).subscribe({
      next: () => {
        this.mensagemService.sucesso('Veículo excluído com sucesso.');
        this.carregarVeiculos();
      },
      error: (err) => {
        const msg: string = err?.error?.message ?? 'Não foi possível excluir o veículo.';
        this.mensagemService.erro(msg);
      }
    });
  }

  salvarVeiculo(form: NgForm): void {
    this.errosFormulario = this.validarFormulario();

    if (form.invalid || this.errosFormulario.length) {
      this.mensagemService.aviso('Revise os campos obrigatórios antes de salvar o veículo.');
      return;
    }

    const operacao$ = this.editando && this.idEditando
      ? this.veiculosService.atualizar(this.idEditando, { ...this.novoVeiculo })
      : this.veiculosService.adicionar({ ...this.novoVeiculo });

    operacao$.subscribe({
      next: () => {
        this.mensagemService.sucesso(this.editando ? 'Veículo atualizado com sucesso.' : 'Veículo salvo com sucesso.');
        form.resetForm(this.criarVeiculoVazio());
        this.formAberto = false;
        this.editando = false;
        this.idEditando = null;
        this.novoVeiculo = this.criarVeiculoVazio();
        this.carregarVeiculos();
      },
      error: (err) => {
        const msg: string = err?.error?.message ?? 'Não foi possível salvar o veículo no momento.';
        this.mensagemService.erro(msg);
      }
    });
  }

  nomeCliente(clienteId: string): string {
    return this.clientesService.todos.find((cliente) => cliente.id === clienteId)?.nome ?? 'Não informado';
  }

  private carregarClientes(): void {
    this.clientesService.listar().subscribe({
      next: (clientes) => {
        this.clientes = clientes;
        this.cdr.detectChanges();
      },
      error: () => {
        this.mensagemService.erro('Falha ao carregar clientes para o cadastro de veículo.');
      }
    });
  }

  private carregarVeiculos(): void {
    this.veiculosService.listar().subscribe({
      next: (veiculos) => {
        this.veiculos = veiculos;
        this.cdr.detectChanges();
      },
      error: () => {
        this.mensagemService.erro('Falha ao carregar veículos.');
      }
    });
  }

  private validarFormulario(): string[] {
    const erros: string[] = [];
    const anoAtual = new Date().getFullYear();

    if (!this.novoVeiculo.idCliente) {
      erros.push('Selecione um cliente.');
    }

    if (!validarPlacaBasica(this.novoVeiculo.placa)) {
      erros.push('Informe uma placa válida (ex.: ABC-1234 ou ABC1D23).');
    }

    if (!this.novoVeiculo.modelo.trim()) {
      erros.push('Informe o modelo do veículo.');
    }

    if (!this.novoVeiculo.marca.trim()) {
      erros.push('Informe a marca do veículo.');
    }

    if (this.novoVeiculo.anoFabricacao < 1950 || this.novoVeiculo.anoFabricacao > anoAtual) {
      erros.push(`Informe um ano entre 1950 e ${anoAtual}.`);
    }

    return erros;
  }

  private criarVeiculoVazio(): Omit<Veiculo, 'id' | 'active'> {
    return {
      idCliente: '',
      placa: '',
      modelo: '',
      marca: '',
      anoFabricacao: new Date().getFullYear(),
      cor: '',
      quilometragem: 0
    };
  }
}