package com.example.demo.security;

import com.example.demo.model.UserEntity;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
public class TokenProvider {

//    public List<Object> Token2() throws JOSEException {
//        String secret = "QQ6Isn9neZglcREU4B+CDRVGjYjWbI3W9MCvmXxUA7M=";
//        String kid = "4e895e3f-2194-442b-9a30-6db2b3d13e59";
//        String clientId = "c4f4468b-50f3-490c-952b-0bad684e4a2a";
//        List<String> scopes = new
//                ArrayList<>(Arrays.asList("tableau:views:embed"));
//        String username =
//                "yskim@dnabro.com"
//                ;
//        JWSSigner signer = new MACSigner(secret);
//        JWSHeader header = new
//                JWSHeader.Builder(JWSAlgorithm.HS256).keyID(kid).customParam("iss", clientId).build();
//        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
//                .issuer(clientId)
//                .expirationTime(new Date(new Date().getTime() + 60 * 10000)) //expires in 10 minute
//                .jwtID(UUID.randomUUID().toString())
//                .audience("tableau")
//                .subject(username)
//                .claim("scp", scopes)
//                .build();
//        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
//        signedJWT.sign(signer);
//
//        List<Object> returnList = new ArrayList<Object>();
//        returnList.add(signedJWT.serialize());
//        return returnList;
//    }

    private static final String SECRET_KEY = "NMA8JPctFuna59f5";

    public TokenProvider() throws JOSEException {
    }

    public String create(UserEntity userEntity){
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );
        /*
        { // header
            "alg" : "HS512"
        }.
        { // payload
         "sub":"40288093784915d201784916a40c0001",
         "iss":"demo app",
         "iat":1595733657,
         "exp":1596597657
         }.
         */
        //JWT toekn 생성
        return Jwts.builder()
                // header에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                //payload에 들어갈 내용
                .setSubject(userEntity.getId()) //sub
                .setIssuer("demo app") //iss
                .setIssuedAt(new Date())//iat
                .setExpiration(expiryDate)//exp
                .compact();
    }

    public String validateAndGetUserId(String token){
        //parseClaimsJws 메서드가 Base64로 디코딩 및 파싱
        // 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
        // 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
        // 그중 우리는 userId가 필요하므로 getBody를 부른다.
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
