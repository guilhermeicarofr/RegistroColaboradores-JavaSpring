package api.colaboradores.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import api.colaboradores.model.Colaborador;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
  Page<Colaborador> findAllBy(Pageable pageable);
}
