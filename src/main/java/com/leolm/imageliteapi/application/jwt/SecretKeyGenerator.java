package com.leolm.imageliteapi.application.jwt;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Objects;

public class SecretKeyGenerator {

    private SecretKey key;

    public SecretKey getKey(){
      if(Objects.isNull(key)){
          key = Jwts.SIG.HS256.key().build();
      }
      return key;
    };
}
