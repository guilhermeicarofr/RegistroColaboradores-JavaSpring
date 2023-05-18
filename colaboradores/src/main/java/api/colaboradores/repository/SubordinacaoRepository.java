package api.colaboradores.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.colaboradores.model.Subordinacao;
import java.util.List;

public interface SubordinacaoRepository extends JpaRepository<Subordinacao, Long> {
  //Encontra a relação de subordinação pelo id do subordinado
  List<Subordinacao> findBySubordinado(long subordinado);
}
