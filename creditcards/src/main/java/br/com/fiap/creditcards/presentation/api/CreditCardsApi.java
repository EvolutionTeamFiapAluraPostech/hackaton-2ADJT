package br.com.fiap.creditcards.presentation.api;

import br.com.fiap.creditcards.presentation.dto.CreditCardInputDto;
import br.com.fiap.creditcards.presentation.dto.CreditCardOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "CreditCardsApi", description = "API de cadastro de cartão de crédito")
public interface CreditCardsApi {

  @Operation(summary = "Cadastro de cartão de crédito",
      description = "Endpoint para cadastrar novos cartões de crédito por cliente.",
      tags = {"CreditCardsApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = CreditCardOutputDto.class))}),
      @ApiResponse(responseCode = "400",
          description = "bad request para validação de cpf, limite, número do cartão, data de validade e código de segurança.",
          content = {@Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "401", description = "unauthorized para usuário não autenticado", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "403", description = "forbidden número máximo de cartões permitidos", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "500",
          description = "Internal Server Error para validação de cpf, limite, número do cartão, data de validade e código de segurança.",
          content = {@Content(schema = @Schema(hidden = true))}),
  })
  void postCreditCard(CreditCardInputDto creditCardInputDto);

  @Operation(summary = "Pesquisa de cartão de crédito",
      description = "Endpoint para pesquisar cartões de crédito cadastrados por número e por cpf do cliente.",
      tags = {"CreditCardsApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = CreditCardOutputDto.class))}),
      @ApiResponse(responseCode = "400",
          description = "bad request para validação de cpf.",
          content = {@Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "401", description = "unauthorized para consulta com usuário não autenticado", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para cartão de crédito não encontrado", content = {
          @Content(schema = @Schema(hidden = true))}),
  })
  CreditCardOutputDto getCreditCardByNumberAndCustomerCpf(@PathVariable String number, @PathVariable String cpf);
}
