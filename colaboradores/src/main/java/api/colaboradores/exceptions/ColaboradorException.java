package api.colaboradores.exceptions;

import org.springframework.http.HttpStatus;

public class ColaboradorException extends RuntimeException {
  public static final String MESSAGE = "Colaborador não encontrado ID:";
  public static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public final String messageId;

  public ColaboradorException(long id) {
    this.messageId = Long.toString(id);
  }
}
