package api.colaboradores.exceptions;

import org.springframework.http.HttpStatus;

public class AnoParamException extends RuntimeException {
  public static final String MESSAGE = "Parâmetro Ano numérico de 4 dígitos - aaaa";
  public static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
}
