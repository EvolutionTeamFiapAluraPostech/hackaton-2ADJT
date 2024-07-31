package br.com.fiap.creditcards.presentation.api;

import br.com.fiap.creditcards.presentation.dto.CreditCardInputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CreditCardsApi", description = "API de cadastro de cartão de crédito")
public interface CreditCardsApi {

  @Operation(summary = "Cadastro de cartão de crédito",
      description = "Endpoint para cadastrar novos cartões de crédito por cliente.",
      tags = {"CreditCardsApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = CreditCardInputDto.class))}),
      @ApiResponse(responseCode = "400",
          description = "bad request para validação de cpf, limite, número do cartão, data de validade e código de segurança.",
          content = {@Content(schema = @Schema(hidden = true))})})
  void postCreditCard(CreditCardInputDto creditCardInputDto);
}
