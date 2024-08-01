package br.com.fiap.payments.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PaymentDto", description = "DTO de saída de dados do pagamento.")
public record PaymentDto(
    @Schema(example = "1000.00", description = "Valor do pagamento para a compra.", minimum = "0.01", format = "0.00")
    String valor,
    @Schema(example = "Compra de produto X", description = "Descrição da compra.")
    String descricao,
    @Schema(example = "Cartão de crédito", description = "Método de pagamento da compra.")
    String metodo_pagamento,
    @Schema(example = "Aprovado/Reprovado", description = "Descrição do status do pagamento.")
    String status
) {

}
