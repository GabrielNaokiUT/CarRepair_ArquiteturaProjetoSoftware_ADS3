package br.edu.senai.fatesg.ads3.car_repair.core.exceptions;

/**
 *
 * @author Caio4breu
 * @author Nomscodes
 * @author GabrielNaokiUT
 */

public enum Severity {
	INFO, // Informativo
	WARNING, // Alerta (regra de negócio contornável)
	ERROR, // Erro impeditivo
	FATAL // Erro crítico de sistema
}
