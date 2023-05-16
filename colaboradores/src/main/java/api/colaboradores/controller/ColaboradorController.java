package api.colaboradores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.colaboradores.dto.ColaboradorDTO;
import api.colaboradores.service.ColaboradorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/colaborador")
public class ColaboradorController {
  @Autowired
  private ColaboradorService service;

  @PostMapping
  public void post(@RequestBody @Valid ColaboradorDTO body) {
    service.create(body);
  }
}
