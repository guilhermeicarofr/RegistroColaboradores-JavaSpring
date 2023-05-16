package api.colaboradores.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import api.colaboradores.dto.ColaboradorDTO;
import api.colaboradores.model.Colaborador;
import api.colaboradores.model.ColaboradorComHierarquias;
import api.colaboradores.repository.ColaboradorRepository;

@Service
public class ColaboradorService {
  @Autowired
  private ColaboradorRepository repository;

  @Autowired
  private SubordinacaoService subordinacaoService;

  public void create(ColaboradorDTO dto) {
    List<Colaborador> cpfEmUso = repository.findByCpf(dto.cpf());
    if(cpfEmUso.isEmpty()) throw new Error("CPF em uso");

    Colaborador colaborador = repository.save(new Colaborador(dto));
    if(dto.gerente() != null) subordinacaoService.createRelacaoGerente(colaborador.getId(), dto);
    subordinacaoService.createRelacaoSubordinados(colaborador.getId(), dto);
  }

  public List<Colaborador> readByPage(Pageable pageable) {
    return repository.findAllBy(pageable).getContent();
  }

  public ColaboradorComHierarquias readById(long id) {
    Optional<Colaborador> colaborador = repository.findById(id);
    if(!colaborador.isPresent()) throw new Error("Colaborador não encontrado");

    Optional<Colaborador> gerente = repository.findGerenteColaborador(id);
    List<Colaborador> subordinados = repository.findSubordinadosColaborador(id);

    return (new ColaboradorComHierarquias(colaborador.get(), gerente, subordinados));
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
