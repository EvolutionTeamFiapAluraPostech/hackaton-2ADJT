package br.com.fiap.payments.presentation.api;

import br.com.fiap.payments.presentation.dto.PaymentInputDto;
import br.com.fiap.payments.presentation.dto.PaymentOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PaymentsApi", description = "API de cadastro de cartão de pagamentos de compra por cartão de crédito")
public interface PaymentsApi {

  @Operation(summary = "Cadastro de pagamento de compra por cartão de crédito",
      description = "Endpoint para cadastrar novos pagamentos de compras por cartões de crédito.",
      tags = {"PaymentsApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentOutputDto.class))}),
      @ApiResponse(responseCode = "401", description = "unauthorized para usuário não autenticado", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "402", description = "para limite de crédito insuficiente", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "500", description = "para erro de negócio", content = {
          @Content(schema = @Schema(hidden = true))})
  })
  PaymentOutputDto postPayment(PaymentInputDto paymentInputDto);
}
