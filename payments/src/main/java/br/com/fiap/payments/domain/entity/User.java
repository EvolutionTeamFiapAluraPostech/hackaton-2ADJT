package br.com.fiap.payments.domain.entity;

public class User {

  private final String id;
  private final String sub;
  private final String iss;
  private final Long exp;

  public User(String id, String sub, String iss, Long exp) {
    this.id = id;
    this.sub = sub;
    this.iss = iss;
    this.exp = exp;
  }

  public String getId() {
    return id;
  }

  public String getSub() {
    return sub;
  }

  public String getIss() {
    return iss;
  }

  public Long getExp() {
    return exp;
  }
}
