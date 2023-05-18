package api.colaboradores.dto;

import jakarta.validation.constraints.NotNull;

//Valida o input de ids de colaboradores da rota de hierarquia
public record ResponsavelHierarquiaDTO(
  @NotNull
  Long colaborador1,

  @NotNull
  Long colaborador2
) {}
