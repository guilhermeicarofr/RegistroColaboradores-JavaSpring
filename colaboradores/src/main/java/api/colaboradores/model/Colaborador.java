package api.colaboradores.model;

import api.colaboradores.dto.ColaboradorDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Colaborador {
  public Colaborador() {}

  public Colaborador(ColaboradorDTO dto) {
    this.cpf = dto.cpf();
    this.nome = dto.nome();
    this.admissao = dto.admissao();
    this.funcao = dto.funcao();
    this.remuneracao = dto.remuneracao();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true)
  private String cpf;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String admissao;

  @Column(nullable = false)
  private String funcao;

  @Column(nullable = false)
  private String remuneracao;
}
