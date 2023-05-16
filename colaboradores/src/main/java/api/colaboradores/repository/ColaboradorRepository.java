package api.colaboradores.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api.colaboradores.model.Colaborador;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
  Page<Colaborador> findAllBy(Pageable pageable);

  List<Colaborador> findByCpf(String cpf);

  @Query(
    value = "SELECT c.id, c.cpf, c.nome, c.admissao, c.funcao, c.remuneracao FROM subordinacao s JOIN colaborador c ON s.gerente = c.id WHERE s.subordinado = :id LIMIT 1",
    nativeQuery = true)
  Optional<Colaborador> findGerenteColaborador(@Param("id") long id);

  @Query(
    value = "SELECT c.id, c.cpf, c.nome, c.admissao, c.funcao, c.remuneracao FROM subordinacao s JOIN colaborador c ON s.subordinado = c.id WHERE s.gerente = :id",
    nativeQuery = true)
  List<Colaborador> findSubordinadosColaborador(@Param("id") long id);
}
