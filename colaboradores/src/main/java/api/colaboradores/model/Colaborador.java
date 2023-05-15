package api.colaboradores.model;

import java.util.Date;

import api.colaboradores.dto.ColaboradorDTO;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Colaborador {
  public Colaborador() {}

  public Colaborador(ColaboradorDTO dto) {
    
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true)
  private String cpf;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private Date admissao;

  @Column(nullable = false)
  private String funcao;

  @Column(nullable = false)
  private int remuneracao;
}
