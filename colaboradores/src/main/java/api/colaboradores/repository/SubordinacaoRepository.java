package api.colaboradores.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.colaboradores.model.Subordinacao;
import java.util.List;


public interface SubordinacaoRepository extends JpaRepository<Subordinacao, Long> {
  List<Subordinacao> findBySubordinado(long subordinado);
}
