package br.com.fiap.payments.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  void shouldCreateUser() {
    var id = UUID.randomUUID().toString();
    var sub = "adj2";
    var iss = "API FIAP";
    var exp = Long.valueOf("1722125523");
    var user = new User(id, sub, iss, exp);

    assertThat(user).isNotNull();
    assertThat(user.getId()).isNotEmpty().isEqualTo(id);
    assertThat(user.getSub()).isNotEmpty().isEqualTo(sub);
    assertThat(user.getIss()).isNotEmpty().isEqualTo(iss);
    assertThat(user.getExp()).isNotNull().isEqualTo(exp);
  }
}