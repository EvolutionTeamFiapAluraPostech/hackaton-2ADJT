package br.com.fiap.payments.domain.entity;

public class Payment {

  private final String id;
  private final String cpf;
  private final String number;
  private final String expirationDate;
  private final String cvv;
  private final String value;

  public Payment(String id, String cpf, String number, String expirationDate, String cvv,
      String accountValue) {
    this.id = id;
    this.cpf = cpf;
    this.number = number;
    this.expirationDate = expirationDate;
    this.cvv = cvv;
    this.value = accountValue;
  }

  public Payment(String cpf, String number, String expirationDate, String cvv,
      String accountValue) {
    this(null, cpf, number, expirationDate, cvv, accountValue);
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
}
