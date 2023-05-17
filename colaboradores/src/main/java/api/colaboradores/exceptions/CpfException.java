package api.colaboradores.exceptions;

import org.springframework.http.HttpStatus;

public class CpfException extends RuntimeException {
  public static final String MESSAGE = "CPF em uso";
  public static final HttpStatus STATUS = HttpStatus.CONFLICT;
}
