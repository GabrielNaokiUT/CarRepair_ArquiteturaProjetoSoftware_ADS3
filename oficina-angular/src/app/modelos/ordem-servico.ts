export type StatusOrdemServico = 'aberta' | 'em_execucao' | 'finalizada' | 'cancelada';

export interface ServicoExecutado {
  descricao: string;
  valor: number;
  tempoExecucaoHoras: number;
}

export interface PecaAplicada {
  descricao: string;
  quantidade: number;
  valorUnitario: number;
}

export interface OrdemServico {
  id: string;
  idCliente: string;
  idVeiculo: string;
  idMecanicoResponsavel: string;
  idUsuarioResponsavel: string;
  dataAbertura: string;
  statusOrdemServico: StatusOrdemServico;
  descricaoProblema: string;
  idServicosExecutados: string[];
  idPecasAplicadas: string[];
  active: boolean;
}
