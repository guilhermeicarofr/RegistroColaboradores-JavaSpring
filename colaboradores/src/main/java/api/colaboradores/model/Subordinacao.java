package api.colaboradores.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class Subordinacao {
  public Subordinacao() {}

  public Subordinacao(int gerente, int subordinado) {

  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  @ManyToOne
  @JoinColumn(name = "gerente", referencedColumnName = "id")
  private int gerente;

  @Column(nullable = false, unique = true)
  @OneToMany
  @JoinColumn(name = "subordinado", referencedColumnName = "id")
  private int subordinado;
}
