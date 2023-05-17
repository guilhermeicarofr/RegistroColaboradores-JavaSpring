package api.colaboradores.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.colaboradores.dto.ResponsavelHierarquiaDTO;
import api.colaboradores.exceptions.HierarquiaException;
import api.colaboradores.model.ColaboradorComHierarquias;

@Service
public class HierarquiaService {
  @Autowired
  ColaboradorService colaboradorService;

  public ColaboradorComHierarquias readResponsavel(ResponsavelHierarquiaDTO dto) {
    //Mapeando toda a hierarquia do colaborador 1
    //Inserindo num HashMap para acesso em complexidade simples
    Map<Long, Boolean>  hierarquia1 = new HashMap<>();
    ColaboradorComHierarquias colaborador1 = colaboradorService.readById(dto.colaborador1());
    hierarquia1.put(colaborador1.getColaborador().getId(), true);
    while(colaborador1.getGerente().isPresent()) {
      hierarquia1.put(colaborador1.getGerente().get().getId(), true);
      colaborador1 = colaboradorService.readById(colaborador1.getGerente().get().getId());
    }

    //Percorrendo toda a hierarquia do colaborador 2
    //Verificando se tem algum gerente em comum com o mapa de hierarquia do colaborador 1
    ColaboradorComHierarquias colaborador2 = colaboradorService.readById(dto.colaborador2());
    while(colaborador2.getGerente().isPresent()) {
      if(hierarquia1.containsKey(colaborador2.getGerente().get().getId())) {
        return colaboradorService.readById(colaborador2.getGerente().get().getId());
      }    
      colaborador2 = colaboradorService.readById(colaborador2.getGerente().get().getId());
    }
    throw new HierarquiaException("Responsável não encontrado");
  }
}
