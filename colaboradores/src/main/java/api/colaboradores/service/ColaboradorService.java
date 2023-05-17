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
    if(cpfInUse(dto.cpf())) throw new Error("CPF em uso");

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

    Colaborador colaborador = repository.save(new Colaborador(dto));
    if(dto.gerente() != null) subordinacaoService.createRelacaoGerente(colaborador.getId(), dto);

    //deletar relacao de gerente dos subordinados informados
    subordinacaoService.deleteRelacaoSubordinado(dto.subordinados());
    //criar relacao do novo gerente criado para os subordinados    
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
    //verificar se o id editado existe

    //verificar se presidente já existe se funcao = presidente
    //verificar se gerente é null caso funcao = presidente

    //verificar se é presidente para autorizar gerente null
    //verificar se id gerente existe

    //verificar se os ids de subordinados já existem
    //verificar se o presidente não é um dos subordinados
    //alterar ou criar gerente dos subordinados para o user criado

    return repository.findById(id).map(colaborador -> {
      colaborador.setNome(dto.nome());
      colaborador.setAdmissao(dto.admissao());
      colaborador.setFuncao(dto.funcao());
      colaborador.setRemuneracao(dto.remuneracao());
      return repository.save(colaborador);
    });
  }

  public void delete(long id) {
    //verificar se o id existe e retornar erro
    //verificar todas as relacoes de subordinacao que existirem com o id
    //subordinados - editar as relacoes para colocar o gerente do Colaborador apagado no lugar
    
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
}
