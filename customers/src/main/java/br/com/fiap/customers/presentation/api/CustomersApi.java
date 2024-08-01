package br.com.fiap.customers.presentation.api;

import br.com.fiap.customers.presentation.dto.CustomerInputDto;
import br.com.fiap.customers.presentation.dto.CustomerOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CustomersApi", description = "API de cadastro de clientes")
public interface CustomersApi {

  @Operation(summary = "Cadastro de clientes",
      description = "Endpoint para cadastrar novos clientes.",
      tags = {"CustomersApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerInputDto.class))}),
      @ApiResponse(responseCode = "401", description = "unauthorized para usuário não autenticado", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "500", description = "bad request para validação de cpf, nome, e-mail e telefone.",
          content = {@Content(schema = @Schema(hidden = true))}),
  })
  CustomerOutputDto postCustomer(
      @Parameter(description = "DTO com atributos para se cadastrar um novo cliente. Requer validação de cpf, nome, e-mail e telefone.")
      CustomerInputDto customerInputDto);

  @Operation(summary = "Pesquisa de cliente por CPF",
      description = "Endpoint para pesquisar um cliente por CPF", tags = "CustomersApi")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerOutputDto.class))}),
      @ApiResponse(responseCode = "401", description = "unauthorized para usuário não autenticado", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para cliente não encontrado",
          content = {@Content(schema = @Schema(hidden = true))})
  })
  CustomerOutputDto getCustomerByCpf(
      @Parameter(description = "Número de CPF do cliente") String cpf);
}
