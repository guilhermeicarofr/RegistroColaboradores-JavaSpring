package api.colaboradores.exceptions;

import org.springframework.http.HttpStatus;

public class HierarquiaException extends RuntimeException {
  public static final HttpStatus STATUS = HttpStatus.CONFLICT;
  public final String message;

  public HierarquiaException(String message) {
    this.message = message;
  }
}
