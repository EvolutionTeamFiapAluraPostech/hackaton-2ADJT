package br.com.fiap.creditcards.presentation.dto;

import br.com.fiap.creditcards.domain.entity.CreditCard;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CreditCardInputDto", description = "DTO de saída de dados do cartão de crédito")
public record CreditCardOutputDto(
    @Schema(example = "fc9ca35f-e911-4bdd-9b73-a6ddc622d0fd", description = "Identificador único do cartão de crédito, gerado automaticamente pelo sistema.")
    String id,
    @Schema(example = "72387289315", description = "Número de cadastro de pessoa física.", minLength = 11, maxLength = 11)
    String cpf,
    @Schema(example = "1000.00", description = "Valor limite para o cartão de crédito.", minimum = "0.00", format = "0.00")
    String limite,
    @Schema(example = "1234567890123456", description = "Número do cartão de crédito.", minLength = 16, maxLength = 16)
    String numero,
    @Schema(example = "12/24", description = "Data de validade do cartão de crédito.", minLength = 5, maxLength = 5)
    String data_validade,
    @Schema(example = "123", description = "Código de segurança do cartão de crédito.", minLength = 3, maxLength = 3)
    String cvv) {

  public static CreditCardOutputDto from(CreditCard creditCard) {
    return new CreditCardOutputDto(creditCard.getId(), creditCard.getCpf(), creditCard.getLimit(),
        creditCard.getNumber(), creditCard.getExpirationDate(), creditCard.getCvv());
  }
}
