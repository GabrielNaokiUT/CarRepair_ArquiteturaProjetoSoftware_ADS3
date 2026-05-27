/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 */

export interface Veiculo {
    id: string;
    idCliente: string;
    placa: string;
    modelo: string;
    marca: string;
    anoFabricacao: number;
    cor: string;
    quilometragem: number;
    active: boolean;
}