package br.com.fiap.creditcards.infrastructure.httpclient.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CustomerDto", description = "DTO com o ID do cliente")
public record CustomerDto(
    @Schema(example = "dcd3398e-4988-4fba-b8c0-a649ae1ff677", description = "Identificador único do cliente cadastrado.")
    String id_cliente
) {
}
