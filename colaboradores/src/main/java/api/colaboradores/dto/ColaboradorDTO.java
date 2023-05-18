package api.colaboradores.dto;

import java.util.List;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

//Faz a validação do input de POST e PUT de Colaborador com id gerente e lista de ids subordinados
public record ColaboradorDTO(
  @NotBlank
  @Pattern(regexp = "[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}", message = "CPF no padrão 123.456.789-10")
  String cpf,
  
  @NotBlank(message = "Nome não pode estar em branco")
  String nome,

  @NotBlank
  @Pattern(regexp = "[0-9]{2}/[0-9]{2}/[0-9]{4}", message = "Admissão no formato dd/mm/aaaa")
  String admissao,

  @NotBlank
  @Pattern(regexp = "[a-z]+", message = "Função deve possuir apenas caracteres minúsculos")
  String funcao,

  @NotNull
  @Pattern(regexp = "[0-9]+,[0-9]{2}", message = "Remuneração em formato 0,00")
  String remuneracao,

  @Nullable
  Long gerente,

  @NotNull
  List<Long> subordinados
) {}
