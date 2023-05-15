package api.colaboradores.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ColaboradorDTO(
  @NotBlank @Pattern(regexp = "[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}") String cpf,
  @NotBlank String nome,
  @NotBlank @Pattern(regexp = "[0-9]{2}/[0-9]{2}/[0-9]{4}") String admissao,
  @NotBlank String funcao,
  @NotNull @Pattern(regexp = "[0-9]+,[0-9]{2}") String remuneracao,
  @Min(1) long gerente
) {}
