package api.colaboradores.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import api.colaboradores.exceptions.AnoParamException;
import api.colaboradores.exceptions.ColaboradorException;
import api.colaboradores.exceptions.CpfException;
import api.colaboradores.exceptions.HierarquiaException;

@ControllerAdvice
public class ExceptionHandlerController {
  
  @ExceptionHandler(CpfException.class)
  public ResponseEntity<String> handleCpfException(CpfException e) {
    return ResponseEntity.status(CpfException.STATUS).body(CpfException.MESSAGE);
  }

  @ExceptionHandler(ColaboradorException.class)
  public ResponseEntity<String> handleColaboradorException(ColaboradorException e) {
    return ResponseEntity.status(ColaboradorException.STATUS).body(ColaboradorException.MESSAGE + e.messageId);
  }

  @ExceptionHandler(HierarquiaException.class)
  public ResponseEntity<String> handleHierarquiaException(HierarquiaException e) {
    return ResponseEntity.status(HierarquiaException.STATUS).body(e.message);
  }

  @ExceptionHandler(AnoParamException.class)
  public ResponseEntity<String> handleAnoParamException(AnoParamException e) {
    return ResponseEntity.status(AnoParamException.STATUS).body(AnoParamException.MESSAGE);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException e) {
    List<String> validationErrors = new ArrayList<>();
    List<ObjectError> errors = e.getBindingResult().getAllErrors();
    for(int i=0; i<errors.size(); i++) {
      validationErrors.add(errors.get(i).getDefaultMessage());
    }

    return ResponseEntity.badRequest().body(validationErrors);
  }
}
