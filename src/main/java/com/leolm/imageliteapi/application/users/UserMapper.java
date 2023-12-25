package com.leolm.imageliteapi.application.users;

import com.leolm.imageliteapi.domain.entity.User;
import org.springframework.stereotype.Component;

public class UserMapper {

    public static User mapToUser(UserDTO userDto){
        return new User().builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .password(userDto.getPassword()).build();
    }
}
