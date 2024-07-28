package br.com.fiap.customers.domain.entity;

import br.com.fiap.customers.domain.valueobject.Cpf;
import br.com.fiap.customers.domain.valueobject.CustomerName;
import br.com.fiap.customers.domain.valueobject.Email;
import br.com.fiap.customers.domain.valueobject.PhoneNumber;

public class Customer {

  private final String id;
  private final String name;
  private final String cpf;
  private final String email;
  private final String phoneNumber;
  private final String street;
  private final String city;
  private final String state;
  private final String country;
  private final String postalCode;

  public Customer(String name, String cpf, String email, String phoneNumber, String street,
      String city, String state, String country, String postalCode) {
    this(null, name, cpf, email, phoneNumber, street, city, state, country, postalCode);
  }

  public Customer(String id, String name, String cpf, String email, String phoneNumber,
      String street, String city, String state, String country, String postalCode) {
    this.id = id;
    this.name = new CustomerName(name).getName();
    this.cpf = new Cpf(cpf).getCpfValue();
    this.email = new Email(email).address();
    this.phoneNumber = new PhoneNumber(phoneNumber).phoneNumberValue();
    this.street = street;
    this.city = city;
    this.state = state;
    this.country = country;
    this.postalCode = postalCode;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getCpf() {
    return cpf;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getStreet() {
    return street;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getCountry() {
    return country;
  }

  public String getPostalCode() {
    return postalCode;
  }
}
