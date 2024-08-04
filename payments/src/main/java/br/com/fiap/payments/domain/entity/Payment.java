package br.com.fiap.payments.domain.entity;

import br.com.fiap.payments.domain.valueobject.AccountValue;
import br.com.fiap.payments.domain.valueobject.Cpf;
import br.com.fiap.payments.domain.valueobject.CreditCardNumber;
import br.com.fiap.payments.domain.valueobject.Cvv;
import br.com.fiap.payments.domain.valueobject.ExpirationDate;
import java.math.BigDecimal;

public class Payment {

  private final String id;
  private final String cpf;
  private final String number;
  private final String expirationDate;
  private final String cvv;
  private final String value;
  private final String status;

  public Payment(String id, String cpf, String number, String expirationDate, String cvv,
      String accountValue, String status) {
    this.id = id;
    this.cpf = new Cpf(cpf).getCpfValue();
    this.number = new CreditCardNumber(number).getNumber();
    this.expirationDate = new ExpirationDate(expirationDate).getExpirationDateValue();
    this.cvv = new Cvv(cvv).getCvvValue();
    this.value = new AccountValue(new BigDecimal(accountValue)).paymentValue().toString();
    this.status = status;
  }

  public Payment(String cpf, String number, String expirationDate, String cvv,
      String accountValue, String status) {
    this(null, cpf, number, expirationDate, cvv, accountValue, status);
  }

  public String getId() {
    return id;
  }

  public String getCpf() {
    return cpf;
  }

  public String getNumber() {
    return number;
  }

  public String getExpirationDate() {
    return expirationDate;
  }

  public String getCvv() {
    return cvv;
  }

  public String getValue() {
    return value;
  }

  public String getStatus() {
    return status;
  }
}
