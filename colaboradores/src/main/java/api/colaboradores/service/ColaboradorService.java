package api.colaboradores.service;

import java.util.ArrayList;
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
    if(cpfInUse(dto.cpf())) throw new Error("CPF em uso");
    validateColaborador(dto);

    Colaborador colaborador = repository.save(new Colaborador(dto));
    if(dto.gerente() != null) subordinacaoService.createRelacaoGerente(colaborador.getId(), dto);
    subordinacaoService.deleteRelacaoSubordinado(dto.subordinados());
    subordinacaoService.createRelacaoSubordinados(colaborador.getId(), dto.subordinados());
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

  public ColaboradorComHierarquias update(long id, ColaboradorDTO dto) {
    ColaboradorComHierarquias target = readById(id);
    validateColaborador(dto);

    repository.findById(id).map(c -> {
      c.setNome(dto.nome());
      c.setAdmissao(dto.admissao());
      c.setFuncao(dto.funcao());
      c.setRemuneracao(dto.remuneracao());
      return repository.save(c);
    });

    List<Long> subordinadoList = new ArrayList<>();
    subordinadoList.add(id);
    subordinacaoService.deleteRelacaoSubordinado(subordinadoList);
    if(dto.gerente() != null) subordinacaoService.createRelacaoGerente(id, dto);
    
    subordinacaoService.deleteRelacaoSubordinado(mapColaboradoresId(target.getSubordinados()));
    if(target.getGerente().isEmpty() && target.getColaborador().getFuncao().equals("presidente")) {
      subordinacaoService.createRelacaoSubordinados(id, mapColaboradoresId(target.getSubordinados()));
    } else {
      subordinacaoService.createRelacaoSubordinados(target.getGerente().get().getId(), mapColaboradoresId(target.getSubordinados()));
    }

    subordinacaoService.deleteRelacaoSubordinado(dto.subordinados());
    subordinacaoService.createRelacaoSubordinados(id, dto.subordinados());

    return readById(id);
  }

  public void delete(long id) {    
    ColaboradorComHierarquias target = readById(id);

    List<Long> subordinadoList = new ArrayList<>();
    subordinadoList.add(id);
    subordinacaoService.deleteRelacaoSubordinado(subordinadoList);
  
    subordinacaoService.deleteRelacaoSubordinado(mapColaboradoresId(target.getSubordinados()));
    if(target.getGerente().isPresent()) {
      subordinacaoService.createRelacaoSubordinados(target.getGerente().get().getId(), mapColaboradoresId(target.getSubordinados()));
    } else {
      subordinacaoService.createRelacaoSubordinados(target.getSubordinados().get(0).getId(), mapColaboradoresId(target.getSubordinados()));
      List<Long> substitutoList = new ArrayList<>();
      substitutoList.add(target.getSubordinados().get(0).getId());
      subordinacaoService.deleteRelacaoSubordinado(substitutoList);
    }

    repository.deleteById(id);
  }

  private boolean cpfInUse(String cpf) {
    List<Colaborador> list = repository.findByCpf(cpf);
    return (!list.isEmpty());
  }

  private boolean presidenteExists() {
    List<Colaborador> list = repository.findByFuncao("presidente");
    return (!list.isEmpty());
  }

  private void validateColaborador(ColaboradorDTO dto) {
    //verificar se presidente já existe se funcao = presidente
    //verificar se gerente é null caso funcao = presidente
    if(dto.funcao().equals("presidente")) {
      if(presidenteExists()) throw new Error("Presidente já é um cargo ocupado");
      if(dto.gerente() != null) throw new Error("A funcao presidente não pode ter um gerente");
    }

    //verificar se é presidente para autorizar gerente null
    if(dto.gerente() == null && !dto.funcao().equals("presidente")) {
      throw new Error("Apenas o presidente pode não possuir gerente");
    }
    //verificar se id gerente existe
    if(dto.gerente() != null) readById(dto.gerente());

    //verificar se os ids de subordinados já existem
    //verificar se o presidente não é um dos subordinados
    //verificar se gerente não está entre os subordinados
    for(int i=0; i<dto.subordinados().size(); i++) {
      if(dto.subordinados().get(i).equals(dto.gerente())) throw new Error("Gerente não pode estar entre os subordinados");

      ColaboradorComHierarquias subordinado = readById(dto.subordinados().get(i));
      if(subordinado.getColaborador().getFuncao().equals("presidente")) {
        throw new Error("Funcao presidente não pode ser um subordinado");
      }
    }
  }

  private List<Long> mapColaboradoresId(List<Colaborador> colaboradores) {
    List<Long> ids = new ArrayList<>();
    for(int i=0; i<colaboradores.size(); i++) {
      ids.add(colaboradores.get(i).getId());
    }
    return ids;
  }
}
