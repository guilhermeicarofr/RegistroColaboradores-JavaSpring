package api.colaboradores.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Rota de teste para verificar saúde da API
@RestController
@RequestMapping("/health")
public class HealthController {
  @GetMapping
  public String health() {
    return("Application is running!");
  }
}
