package api.colaboradores.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import api.colaboradores.dto.ColaboradorDTO;
import api.colaboradores.exceptions.AnoParamException;
import api.colaboradores.exceptions.ColaboradorException;
import api.colaboradores.exceptions.CpfException;
import api.colaboradores.exceptions.HierarquiaException;
import api.colaboradores.model.Colaborador;
import api.colaboradores.model.ColaboradorComHierarquias;
import api.colaboradores.repository.ColaboradorRepository;

@Service
public class ColaboradorService {
  @Autowired
  private ColaboradorRepository repository;

  @Autowired
  private SubordinacaoService subordinacaoService;

  //Criacao de colaborador
  public Colaborador create(ColaboradorDTO dto) {
    //verifica se o cpf já está em uso
    if(cpfInUse(dto.cpf())) throw new CpfException();
    //valida a consistencia de todos os dados enviados
    validateColaborador(dto);

    //cria o colaborador
    Colaborador colaborador = repository.save(new Colaborador(dto));
    //cria a relacao do colaborador com seu gerente, caso exista
    if(dto.gerente() != null) subordinacaoService.createRelacaoGerente(colaborador.getId(), dto);
    //apaga as relacoes de subordinacao anteriores dos subordinados informados
    subordinacaoService.deleteRelacaoSubordinado(dto.subordinados());
    //cria as novas relacoes de subordinacao com o novo colaborador como Gerente dos seus subordinados
    subordinacaoService.createRelacaoSubordinados(colaborador.getId(), dto.subordinados());

    return colaborador;
  }

  //retorna lista de colaboradores paginada
  public List<Colaborador> readPage(Pageable pageable) {
    return repository.findAllBy(pageable).getContent();
  }

  //retorna um colaborador com dados de gerente e subordinados pelo id
  public ColaboradorComHierarquias readById(long id) {
    Optional<Colaborador> colaborador = repository.findById(id);
    if(!colaborador.isPresent()) throw new ColaboradorException(id);

    //busca dados do gerente
    Optional<Colaborador> gerente = repository.findGerenteColaborador(id);
    //busca dados dos subordinados
    List<Colaborador> subordinados = repository.findSubordinadosColaborador(id);

    //Cria a classe ColaboradorComHierarquias com os dados obtidos
    return (new ColaboradorComHierarquias(colaborador.get(), gerente, subordinados));
  }

  //Edita um colaborador existente
  public ColaboradorComHierarquias update(long id, ColaboradorDTO dto) {
    //verifica se o colaborador existe
    ColaboradorComHierarquias target = readById(id);
    //valida a consistencia de todos os dados enviados
    validateColaborador(dto);

    //Edita o registro de Colaborador
    repository.findById(id).map(c -> {
      c.setNome(dto.nome());
      c.setAdmissao(dto.admissao());
      c.setFuncao(dto.funcao());
      c.setRemuneracao(dto.remuneracao());
      return repository.save(c);
    });

    //Deleta antiga relacao do colaborador edita com seu antigo gerente
    List<Long> subordinadoList = new ArrayList<>();
    subordinadoList.add(id);
    subordinacaoService.deleteRelacaoSubordinado(subordinadoList);
    //Cria nova relacao do colaborador editado com seu novo gerente
    if(dto.gerente() != null) subordinacaoService.createRelacaoGerente(id, dto);
    
    //Deleta relacoes do colaborador editado com seus antigos subordinados
    subordinacaoService.deleteRelacaoSubordinado(mapColaboradoresId(target.getSubordinados()));
    if(target.getGerente().isEmpty() && target.getColaborador().getFuncao().equals("presidente")) {
      //Caso o colaborador editado não possua gerente, seja presidente, ele continua com os antigos subordinados
      subordinacaoService.createRelacaoSubordinados(id, mapColaboradoresId(target.getSubordinados()));
    } else {
      //Caso o colaborador editado possua um gerente, o gerente passa a ser o novo gerente dos seus antigos subordinados
      subordinacaoService.createRelacaoSubordinados(target.getGerente().get().getId(), mapColaboradoresId(target.getSubordinados()));
    }

    //Deleta relacoes dos novos subordinados com seus antigos gerentes
    subordinacaoService.deleteRelacaoSubordinado(dto.subordinados());
    //Cria relacoes dos novos subordinados com o colaborador editado, como novo gerente
    subordinacaoService.createRelacaoSubordinados(id, dto.subordinados());

    return readById(id);
  }

  //Deleta um colaborador
  public void delete(long id) {
    //Verifica se o colaborador existe
    ColaboradorComHierarquias target = readById(id);

    //Deleta a relacao do colaborador apagado com seu gerente
    List<Long> subordinadoList = new ArrayList<>();
    subordinadoList.add(id);
    subordinacaoService.deleteRelacaoSubordinado(subordinadoList);
  
    //Deleta as relacoes dos subordinados com o colaborador apagado
    subordinacaoService.deleteRelacaoSubordinado(mapColaboradoresId(target.getSubordinados()));
    if(target.getGerente().isPresent()) {
      //Se colaborador apagado possuir um gerente, esse passa a ser o gerente dos seus antidos subordinados
      subordinacaoService.createRelacaoSubordinados(target.getGerente().get().getId(), mapColaboradoresId(target.getSubordinados()));
    } else {
      //Se colaborador apagado não possui gerente, o seu primeiro subordinado passa a ser o novo gerente de seus subordinados
      subordinacaoService.createRelacaoSubordinados(target.getSubordinados().get(0).getId(), mapColaboradoresId(target.getSubordinados()));
      //Exclui a relacao de subordinado do novo gerente
      //Este passa a ser o presidente hierarquicamente, porem sua funcao não é alterada
      List<Long> substitutoList = new ArrayList<>();
      substitutoList.add(target.getSubordinados().get(0).getId());
      subordinacaoService.deleteRelacaoSubordinado(substitutoList);
    }

    //Finalmente o colaborador é deletado
    repository.deleteById(id);
  }

  //Retorna a lista de colaboradores admitidos no ano informado
  public List<Colaborador> readPageByAdmissao(String ano, Pageable pageable) {
    //Validacao do input de ano
    if(ano.length() != 4) throw new AnoParamException();
    try {
      Integer.parseInt(ano);
    } catch (NumberFormatException e) {
      throw new AnoParamException();
    }

    return repository.findByAnoAdmissao(ano, pageable).getContent();
  }

  //Verifica se o CPF já está em uso
  private boolean cpfInUse(String cpf) {
    List<Colaborador> list = repository.findByCpf(cpf);
    return (!list.isEmpty());
  }

  //Verifica se o cargo de presidente(unico) já está ocupado
  private boolean presidenteExists() {
    List<Colaborador> list = repository.findByFuncao("presidente");
    return (!list.isEmpty());
  }

  //Funcao responsavel por validar os dados informados na criacao e edicao de Colaborador
  private void validateColaborador(ColaboradorDTO dto) {
    //campo funcao
    //verificar se presidente já existe caso funcao = presidente
    //verificar se gerente é null caso funcao = presidente
    if(dto.funcao().equals("presidente")) {
      if(presidenteExists()) throw new HierarquiaException("Presidente já é um cargo ocupado");
      if(dto.gerente() != null) throw new HierarquiaException("A função presidente não pode ter um gerente");
    }

    //campo gerente
    //verificar se é presidente caso gerente null
    if(dto.gerente() == null && !dto.funcao().equals("presidente")) {
      throw new HierarquiaException("Apenas o presidente pode não possuir gerente");
    }
    //verificar se id gerente existe
    if(dto.gerente() != null) readById(dto.gerente());

    //lista de subordinados
    //verificar se os ids de subordinados existem
    //verificar se o presidente não é um dos subordinados
    //verificar se gerente informado não está entre os subordinados
    for(int i=0; i<dto.subordinados().size(); i++) {
      if(dto.subordinados().get(i).equals(dto.gerente())) throw new HierarquiaException("Gerente não pode estar entre os subordinados");

      ColaboradorComHierarquias subordinado = readById(dto.subordinados().get(i));
      if(subordinado.getColaborador().getFuncao().equals("presidente")) {
        throw new HierarquiaException("Função presidente não pode ser um subordinado");
      }
    }
  }

  //Recebe uma Lista de Colaboradores e retorna uma lista com seus IDs
  private List<Long> mapColaboradoresId(List<Colaborador> colaboradores) {
    List<Long> ids = new ArrayList<>();
    for(int i=0; i<colaboradores.size(); i++) {
      ids.add(colaboradores.get(i).getId());
    }
    return ids;
  }
}
