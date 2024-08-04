package br.com.fiap.payments.infrastructure.httpclient.creditcard.dto;

import java.math.BigDecimal;

public record CreditCardPaymentValueDto(BigDecimal paymentValue) {

}
