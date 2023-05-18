package api.colaboradores.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.colaboradores.dto.ColaboradorDTO;
import api.colaboradores.model.Subordinacao;
import api.colaboradores.repository.SubordinacaoRepository;

@Service
public class SubordinacaoService {
  @Autowired
  private SubordinacaoRepository repository;

  //Cria relacao de subordinacao entre um colaborador e seu gerente
  public void createRelacaoGerente(long id, ColaboradorDTO dto) {
    repository.save(new Subordinacao(dto.gerente(), id));
  }

  //Recebe um gerente e uma lista de ids de subordinados e cria relações de subordinação entre eles
  public void createRelacaoSubordinados(long id, List<Long> subordinados) {
    for(int i=0; i<subordinados.size(); i++) {
      repository.save(new Subordinacao(id, subordinados.get(i)));
    }
  }

  //Recebe uma lista de ids de subordinados e apaga a relacao de subordinacao de todos, deixando sem gerente
  public void deleteRelacaoSubordinado(List<Long> subordinados) {
    for(int i=0; i<subordinados.size(); i++) {
      List<Subordinacao> list = repository.findBySubordinado(subordinados.get(i));
      for(int j=0; j<list.size(); j++) {
        repository.delete(list.get(j));
      }
    }
  }
}
