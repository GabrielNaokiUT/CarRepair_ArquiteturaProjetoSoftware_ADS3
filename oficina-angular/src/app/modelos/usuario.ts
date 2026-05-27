/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 */

export interface Usuario {
    id: string;
    nome: string;
    login: string;
    email: string;
    perfil: 'administrativo' | 'atendente' | 'gestor';
    active: boolean;
}