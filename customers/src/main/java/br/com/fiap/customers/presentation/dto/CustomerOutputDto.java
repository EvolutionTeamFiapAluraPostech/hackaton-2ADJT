package br.com.fiap.customers.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CustomerOutputDto", description = "DTO de saída de dados do cliente")
public record CustomerOutputDto(
    @Schema(example = "dcd3398e-4988-4fba-b8c0-a649ae1ff677", description = "Identificador único do cliente recém cadastrado.")
    String id_cliente
) {
}
