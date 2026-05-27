import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { forkJoin } from 'rxjs';

import { ClientesService } from '../../services/dominios/clientes.service';
import { MecanicosService } from '../../services/dominios/mecanicos.service';
import { OrdensServicoService } from '../../services/dominios/ordens-servico.service';
import { ServicosService } from '../../services/dominios/servicos.service';
import { UsuariosService } from '../../services/dominios/usuarios.service';
import { VeiculosService } from '../../services/dominios/veiculos.service';
import { MensagemService } from '../../shared/mensagens/mensagem.service';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  totalClientes = 0;
  totalVeiculos = 0;
  totalMecanicos = 0;
  totalOrdensServico = 0;
  totalUsuarios = 0;
  totalServicos = 0;

  constructor(
    private readonly clientesService: ClientesService,
    private readonly veiculosService: VeiculosService,
    private readonly mecanicosService: MecanicosService,
    private readonly ordensServicoService: OrdensServicoService,
    private readonly servicosService: ServicosService,
    private readonly usuariosService: UsuariosService,
    private readonly mensagemService: MensagemService,
    private readonly cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    forkJoin({
      clientes: this.clientesService.listar(),
      veiculos: this.veiculosService.listar(),
      mecanicos: this.mecanicosService.listar(),
      ordens: this.ordensServicoService.listar(),
      servicos: this.servicosService.listar(),
      usuarios: this.usuariosService.listar()
    }).subscribe({
      next: ({ clientes, veiculos, mecanicos, ordens, servicos, usuarios }) => {
        this.totalClientes = clientes.length;
        this.totalVeiculos = veiculos.length;
        this.totalMecanicos = mecanicos.length;
        this.totalOrdensServico = ordens.length;
        this.totalServicos = servicos.length;
        this.totalUsuarios = usuarios.length;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error(err);
        this.mensagemService.erro('Não foi possível carregar os indicadores do dashboard.');
      }
    });
  }
}
