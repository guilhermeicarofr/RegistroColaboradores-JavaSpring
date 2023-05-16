package api.colaboradores.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.colaboradores.dto.ColaboradorDTO;
import api.colaboradores.model.Subordinacao;
import api.colaboradores.repository.SubordinacaoRepository;

@Service
public class SubordinacaoService {
  @Autowired
  private SubordinacaoRepository repository;

  public void createRelacaoGerente(long id, ColaboradorDTO dto) {
    repository.save(new Subordinacao(dto.gerente(), id));
  }

  public void createRelacaoSubordinados(long id, ColaboradorDTO dto) {
    for(int i=0; i<dto.subordinados().size(); i++) {
      repository.save(new Subordinacao(id, dto.subordinados().get(i)));
    }
  }
}
