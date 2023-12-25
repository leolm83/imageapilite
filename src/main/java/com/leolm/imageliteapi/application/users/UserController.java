package com.leolm.imageliteapi.application.users;

import com.leolm.imageliteapi.domain.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity save (@RequestBody UserDTO userDto){

        userService.save(UserMapper.mapToUser(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/auth")
    public ResponseEntity authenticate (@RequestBody CredentialDTO credentialDTO){
        var token = userService.authenticate(credentialDTO);
        if(token.isEmpty()){
            throw new AccessDeniedException("verifique as informacoes enviadas ao servidor");
        }
        return  ResponseEntity.ok(token);
    }

}
