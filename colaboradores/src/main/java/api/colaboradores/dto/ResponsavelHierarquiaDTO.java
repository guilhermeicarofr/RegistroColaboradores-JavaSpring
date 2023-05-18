package api.colaboradores.dto;

import jakarta.validation.constraints.NotNull;

public record ResponsavelHierarquiaDTO(
  @NotNull
  Long colaborador1,

  @NotNull
  Long colaborador2
) {}
