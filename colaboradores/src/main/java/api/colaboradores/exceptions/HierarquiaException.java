package api.colaboradores.exceptions;

import org.springframework.http.HttpStatus;

//Throwable relacionado as Exceptions de hierarquia, conflitos entre subordinados e gerentes informados, etc
public class HierarquiaException extends RuntimeException {
  public static final HttpStatus STATUS = HttpStatus.CONFLICT;
  public final String message;

  public HierarquiaException(String message) {
    this.message = message;
  }
}
