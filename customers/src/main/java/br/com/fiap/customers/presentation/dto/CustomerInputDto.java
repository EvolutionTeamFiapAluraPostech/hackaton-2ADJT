package br.com.fiap.customers.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CustomerInputDto", description = "DTO de entrada de dados do cliente")
public record CustomerInputDto(
    @Schema(example = "72387289315", description = "Número de cadastro de pessoa física.", minLength = 11, maxLength = 11)
    String cpf,
    @Schema(example = "João da Silva", description = "Nome do cliente.", minLength = 2, maxLength = 500)
    String nome,
    @Schema(example = "joaodasilva@email.com.br", description = "Endereço de email do cliente.", maxLength = 500)
    String email,
    @Schema(example = "11 91234-5678", description = "Número de telefone do cliente.", maxLength = 50)
    String telefone,
    @Schema(example = "Av. Lins de Vasconcelos", description = "Rua do endereço do cliente.", minLength = 3, maxLength = 255)
    String rua,
    @Schema(example = "São Paulo", description = "Cidade do endereço do cliente.", minLength = 3, maxLength = 100)
    String cidade,
    @Schema(example = "São Paulo", description = "Estado do endereço do cliente.", minLength = 3, maxLength = 100)
    String estado,
    @Schema(example = "01538001", description = "Código postal do endereço do cliente.", minLength = 8, maxLength = 8)
    String cep,
    @Schema(example = "Brasil", description = "País do endereço do cliente.", minLength = 3, maxLength = 100)
    String pais
    ) {
}
