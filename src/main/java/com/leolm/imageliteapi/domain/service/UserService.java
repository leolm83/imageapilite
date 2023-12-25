package com.leolm.imageliteapi.domain.service;

import com.leolm.imageliteapi.application.users.CredentialDTO;
import com.leolm.imageliteapi.domain.AcessToken;
import com.leolm.imageliteapi.domain.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByEmail(String email);
    User save(User user);
    Optional<AcessToken> authenticate(CredentialDTO credentialDTO);
}
