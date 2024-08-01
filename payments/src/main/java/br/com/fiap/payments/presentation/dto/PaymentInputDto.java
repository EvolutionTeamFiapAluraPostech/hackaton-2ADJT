package br.com.fiap.payments.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PaymentInputDto", description = "DTO de entrada de dados do pagamento.")
public record PaymentInputDto(
    @Schema(example = "72387289315", description = "Número de cadastro de pessoa física.", minLength = 11, maxLength = 11)
    String cpf,
    @Schema(example = "1234567890123456", description = "Número do cartão de crédito.", minLength = 16, maxLength = 16)
    String numero,
    @Schema(example = "12/24", description = "Data de validade do cartão de crédito.", minLength = 5, maxLength = 5)
    String data_validade,
    @Schema(example = "123", description = "Código de segurança do cartão de crédito.", minLength = 3, maxLength = 3)
    String cvv,
    @Schema(example = "1000.00", description = "Valor do pagamento para a compra.", minimum = "0.01", format = "0.00")
    String valor) {
}
