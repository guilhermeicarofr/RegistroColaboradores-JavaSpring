package api.colaboradores.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.colaboradores.dto.ColaboradorDTO;
import api.colaboradores.model.Colaborador;
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

  @GetMapping
  public List<Colaborador> getList(@RequestParam("page") String page) {
    int pageSize = 5;
    Pageable pageable = PageRequest.of(Integer.parseInt(page), pageSize);
    return service.readByPage(pageable);
  }
}
