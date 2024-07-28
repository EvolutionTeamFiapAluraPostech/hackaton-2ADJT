package br.com.fiap.customers.infrastructure.security;

import static org.flywaydb.core.internal.util.JsonUtils.getFromJson;

import br.com.fiap.customers.domain.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TokenService {

  private static final String ISSUER = "API FIAP";

  @Value("${api.security.token.secret}")
  private String secret;

  public User getUserFrom(String token) {
    var subject = getSubject(token);
    if (StringUtils.hasLength(subject)) {
      var payload = this.getPayloadFrom(token);
      var id = getFromJson(payload, "id");
      var sub = getFromJson(payload, "sub");
      var iss = getFromJson(payload, "iss");
      var exp = Long.valueOf(getFromJson(payload, "exp"));
      return new User(id, sub, iss, exp);
    }
    return null;
  }

  public String getSubject(String tokenJWT) {
    try {
      var algorithm = Algorithm.HMAC256(this.secret);
      return JWT.require(algorithm)
          .withIssuer(ISSUER)
          .build()
          .verify(tokenJWT)
          .getSubject();
    } catch (JWTVerificationException exception) {
      throw new RuntimeException("Token JWT inválido ou expirado!", exception);
    }
  }

  private String getPayloadFrom(String tokenJWT) {
    var chunks = tokenJWT.split("\\.");
    var decoder = Base64.getUrlDecoder();
    return new String(decoder.decode(chunks[1]));
  }
}
