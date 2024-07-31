package br.com.fiap.creditcards.domain.entity;

import static br.com.fiap.creditcards.domain.valueobject.Limit.LIMIT_FIELD;

import br.com.fiap.creditcards.domain.exception.ValidatorException;
import br.com.fiap.creditcards.domain.valueobject.Cpf;
import br.com.fiap.creditcards.domain.valueobject.CreditCardNumber;
import br.com.fiap.creditcards.domain.valueobject.Cvv;
import br.com.fiap.creditcards.domain.valueobject.ExpirationDate;
import br.com.fiap.creditcards.domain.valueobject.Limit;
import java.math.BigDecimal;
import org.springframework.validation.FieldError;

public class CreditCard {

  private static final String LIMIT_REGEX = "^[0-9]*\\.?[0-9]+$";
  private static final String LIMIT_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE = "O valor do limite não pode ser %s.";
  private static final String LIMIT_VALUE_IS_INVALID_MESSAGE = "O valor do limite é invalido. Você digitou %s.";
  public static final String NULL_VALUE_MESSAGE = "null";
  public static final String EMPTY_VALUE_MESSAGE = "";
  private String id;
  private final String cpf;
  private final String limit;
  private final String number;
  private final String expirationDate;
  private final String cvv;

  public CreditCard(String cpf, String limit, String number, String expirationDate, String cvv) {
    this(null, cpf, limit, number, expirationDate, cvv);
  }

  public CreditCard(String id, String cpf, String limit, String number, String expirationDate,
      String cvv) {
    validateLimitValue(limit);
    this.id = id;
    this.cpf = new Cpf(cpf).getCpfValue();
    this.limit = new Limit(new BigDecimal(limit)).limit().toString();
    this.number = new CreditCardNumber(number).getNumber();
    this.expirationDate = new ExpirationDate(expirationDate).getExpirationDateValue();
    this.cvv = new Cvv(cvv).getCvvValue();
  }

  private void validateLimitValue(String limit) {
    if (limit == null) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), LIMIT_FIELD,
          LIMIT_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE.formatted(NULL_VALUE_MESSAGE)));
    }
    if (limit.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), LIMIT_FIELD,
          LIMIT_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE.formatted(EMPTY_VALUE_MESSAGE)));
    }
    if (!limit.matches(LIMIT_REGEX)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), LIMIT_FIELD,
              LIMIT_VALUE_IS_INVALID_MESSAGE.formatted(limit)));
    }
  }

  public String getId() {
    return id;
  }

  public String getCpf() {
    return cpf;
  }

  public String getLimit() {
    return limit;
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
}
