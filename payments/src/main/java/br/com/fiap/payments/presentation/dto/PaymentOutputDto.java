package br.com.fiap.payments.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PaymentOutputDto", description = "DTO de saída do pagamento.")
public record PaymentOutputDto(
    @Schema(example = "fc9ca35f-e911-4bdd-9b73-a6ddc622d0fd", description = "Identificador único do pagamento, gerado automaticamente pelo sistema.")
    String chave_pagamento) {
}
