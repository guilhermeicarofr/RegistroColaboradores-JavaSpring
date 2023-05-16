package api.colaboradores.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import api.colaboradores.dto.ColaboradorDTO;
import api.colaboradores.model.Colaborador;
import api.colaboradores.repository.ColaboradorRepository;

@Service
public class ColaboradorService {
  @Autowired
  private ColaboradorRepository repository;

  public void create(ColaboradorDTO dto) {
    repository.save(new Colaborador(dto));
  }

  public List<Colaborador> readByPage(Pageable pageable) {
    return repository.findAllBy(pageable).getContent();
  }

  public Optional<Colaborador> readById(long id) {
    return repository.findById(id);
  }

  public Optional<Colaborador> update(long id, ColaboradorDTO dto) {
    return repository.findById(id).map(colaborador -> {
      colaborador.setNome(dto.nome());
      colaborador.setAdmissao(dto.admissao());
      colaborador.setFuncao(dto.funcao());
      colaborador.setRemuneracao(dto.remuneracao());
      return repository.save(colaborador);
    });
  }

  public void delete(long id) {
    repository.deleteById(id);
  }
}
