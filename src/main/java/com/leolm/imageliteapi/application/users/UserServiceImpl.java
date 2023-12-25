package com.leolm.imageliteapi.application.users;

import com.leolm.imageliteapi.application.jwt.JwtService;
import com.leolm.imageliteapi.domain.AcessToken;
import com.leolm.imageliteapi.domain.entity.User;
import com.leolm.imageliteapi.domain.exceptions.UserAlreadyExistsException;
import com.leolm.imageliteapi.domain.service.UserService;
import com.leolm.imageliteapi.infra.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;


@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private Validator validator;

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        Set<ConstraintViolation<User>> errors = validator.validate(user);
        if(!errors.isEmpty()){
            log.info("ERROS FORAM ENCONTRADOS NA REQUISICAO {}",errors.size());
            throw new ConstraintViolationException(errors);
        }
        Optional<User> possibleUser = getByEmail(user.getEmail());
        if(possibleUser.isPresent()){
            throw new UserAlreadyExistsException("The current user already exists in database");
        }
        user.setPassword(encodePassword(user));
        return userRepository.save(user);
    }

    @Override
    public Optional<AcessToken> authenticate(CredentialDTO credentialDTO) {
        Optional<User> possibleUser = getByEmail(credentialDTO.getEmail());

        if(possibleUser.isPresent() && passwordEncoder.matches(credentialDTO.getPassword(),possibleUser.get().getPassword())){
            return Optional.of(jwtService.generateToken(possibleUser.get()));
        }
        return Optional.empty();
    }

    private String encodePassword(User user){
       String raw  = user.getPassword();
       String encodedPassword = passwordEncoder.encode(raw);
       return encodedPassword;
    }
}

