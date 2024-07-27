package br.com.fiap.authentication.user.application.gateway;

import br.com.fiap.authentication.user.domain.entity.User;

public interface UserGateway {

  User findByUsername(String email);
}
