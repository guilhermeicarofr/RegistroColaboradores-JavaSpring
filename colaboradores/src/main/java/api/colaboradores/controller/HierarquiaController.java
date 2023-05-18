package api.colaboradores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import api.colaboradores.dto.ResponsavelHierarquiaDTO;
import api.colaboradores.model.ColaboradorComHierarquias;
import api.colaboradores.service.HierarquiaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/hierarquia")
public class HierarquiaController {
  @Autowired
  private HierarquiaService service;

  @GetMapping("/responsavel")
  @ResponseStatus(HttpStatus.OK)
  public ColaboradorComHierarquias getResponsavel(@RequestBody @Valid ResponsavelHierarquiaDTO dto) {
    return service.readResponsavel(dto);
  }
}
