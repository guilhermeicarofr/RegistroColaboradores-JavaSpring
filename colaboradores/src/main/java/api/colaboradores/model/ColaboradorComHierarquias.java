package api.colaboradores.model;

import java.util.List;
import java.util.Optional;

import lombok.Data;

@Data
public class ColaboradorComHierarquias {
  public ColaboradorComHierarquias() {}

  public ColaboradorComHierarquias(Colaborador colaborador, Optional<Colaborador> gerente, List<Colaborador> subordinados) {
    this.colaborador = colaborador;
    this.gerente = gerente;
    this.subordinados = subordinados;
  }

  private Colaborador colaborador;

  private Optional<Colaborador> gerente;

  private List<Colaborador> subordinados;
}
