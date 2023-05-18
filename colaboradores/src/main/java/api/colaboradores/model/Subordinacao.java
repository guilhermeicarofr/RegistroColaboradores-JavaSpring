package api.colaboradores.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import lombok.Data;

@Data
@Entity
public class Subordinacao {
  public Subordinacao() {}

  public Subordinacao(long gerente, long subordinado) {
    this.gerente = gerente;
    this.subordinado = subordinado;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @JoinTable(
    name = "colaborador",
    joinColumns = @JoinColumn(name = "id"),
    inverseJoinColumns = @JoinColumn(name = "gerente")
  ) private long gerente;

  @JoinTable(
    name = "colaborador",
    joinColumns = @JoinColumn(name = "id"),
    inverseJoinColumns = @JoinColumn(name = "subordinado")
  ) private long subordinado;
}
