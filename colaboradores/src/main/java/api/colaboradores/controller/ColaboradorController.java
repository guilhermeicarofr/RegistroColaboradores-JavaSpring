package api.colaboradores.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @GetMapping("/{id}")
  public Optional<Colaborador> getOne(@PathVariable long id) {
    return service.readById(id);
  }

  @PutMapping("/{id}")
  public void put(@PathVariable long id, @RequestBody @Valid ColaboradorDTO body) {
    service.update(id, body);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable long id) {
    service.delete(id);
  }
}
