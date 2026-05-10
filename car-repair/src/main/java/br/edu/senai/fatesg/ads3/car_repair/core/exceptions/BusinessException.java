package br.edu.senai.fatesg.ads3.car_repair.core.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

@Getter
public class BusinessException extends BaseException {

    private static final long serialVersionUID = -224350778964358350L;

    public BusinessException(String message, HttpStatus httpStatus) {
        super(
                "Erro de Processo de Negócio", // Título fixo para este tipo de exceção
                message,
                httpStatus,
                Severity.ERROR,
                "BUSINESS_PROCESS_ERROR"
        );
    }

    // Construtor padrão
    public BusinessException(String message) {
        super("Erro de Negócio", message, HttpStatus.UNPROCESSABLE_ENTITY, Severity.ERROR, "BUSINESS_ERROR");
    }

    /**
     * Construtor de Conversão: Recebe qualquer Exception e a envelopa. Útil
     * para capturar erros inesperados e dar um contexto de negócio.
     */
    public BusinessException(Throwable cause) {
        super("Erro Interno de Processamento", cause.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Severity.FATAL,
                "INTERNAL_SERVER_ERROR");
        this.initCause(cause); // Mantém a causa original para o log (StackTrace)
    }

    /**
     * Construtor de Conversão com Mensagem Customizada. Ex: "Falha ao salvar
     * Ala" + Exceção original do SQL.
     */
    public BusinessException(String customMessage, Throwable cause) {
        super("Erro de Operação", customMessage, HttpStatus.INTERNAL_SERVER_ERROR, Severity.ERROR, "OPERATION_FAILED");
        this.initCause(cause);
    }

    /**
     * Construtor com motive e status separados. Usado pelo
     * handleDataAccessException para erros de integridade do banco.
     */
    public BusinessException(String message, String motive, HttpStatus httpStatus) {
        super("Erro de Negócio", message, httpStatus, Severity.ERROR, motive);
    }

    /**
     * Construtor com motive, status e cause. Usado pelo
     * handleDataAccessException quando há uma exceção original do banco.
     */
    public BusinessException(String message, String motive, HttpStatus httpStatus, Throwable cause) {
        super("Erro de Negócio", message, httpStatus, Severity.ERROR, motive);
        this.initCause(cause);
    }

    public static void handleDataAccessException(DataAccessException dae, String entityName) {
        if (dae instanceof DataIntegrityViolationException dive) {
            String message = dive.getMostSpecificCause().getMessage();
            if (message != null && message.contains("duplicate key value violates unique constraint")) {
                String fieldName = extractFieldNameFromUniqueError(message);
                throw new BusinessException(
                        "Valor duplicado para o campo: " + fieldName + ". Este valor já existe no sistema.",
                        "DUPLICATE_KEY",
                        HttpStatus.CONFLICT
                );
            }
            if (message != null && message.contains("foreign key constraint")) {
                throw new BusinessException(
                        "Não é possível processar esta operação. Verifique as referências a outras entidades.",
                        "FOREIGN_KEY_VIOLATION",
                        HttpStatus.BAD_REQUEST
                );
            }
        }
        throw new BusinessException(
                "Erro de integridade ao processar " + entityName + ". Verifique os dados enviados.",
                "DATA_INTEGRITY_ERROR",
                HttpStatus.BAD_REQUEST,
                dae
        );
    }

    private static String extractFieldNameFromUniqueError(String message) {
        if (message.contains("Key (")) {
            int start = message.indexOf("Key (") + 5;
            int end = message.indexOf(")", start);
            if (end > start) {
                return message.substring(start, end);
            }
        }
        return "desconhecido";
    }
}
