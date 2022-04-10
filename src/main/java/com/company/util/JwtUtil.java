package com.company.util;

import com.company.dto.ProfileJwtDto;
import com.company.enums.ProfileRole;
import com.company.exceptions.ForbiddenException;
import com.company.exceptions.UnauthorizedException;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {
    private final static String secretKey = "kalitso'z";

    public static String createJwt(Integer id,ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(String.valueOf(id));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)));
        jwtBuilder.setIssuer("olx.com");
        jwtBuilder.claim("role", role.name());

        String jwt = jwtBuilder.compact();
        return jwt;
    }

    public static String createJwtById(Integer id) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.setSubject(String.valueOf(id));
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)));
        jwtBuilder.setIssuer("olx.com");

        String jwt = jwtBuilder.compact();
        return jwt;
    }

    public static ProfileJwtDto decodeJwt(String jwt) {
        JwtParser jwtParser = Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws jws = jwtParser.parseClaimsJws(jwt);

        Claims claims = (Claims) jws.getBody();
        String id = claims.getSubject();
        ProfileRole role = ProfileRole.valueOf((String) claims.get("role"));
        ProfileJwtDto jwtDto=new ProfileJwtDto();
        jwtDto.setId(Integer.parseInt(id));
        jwtDto.setRole(role);

        return jwtDto;
    }

    public static Integer decodeJwtAndGetId(String jwt) {
        JwtParser jwtParser = Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws jws = jwtParser.parseClaimsJws(jwt);

        Claims claims = (Claims) jws.getBody();
        String id = claims.getSubject();

        return Integer.parseInt(id);
    }

    public static Integer getCurrentUser(HttpServletRequest request) throws RuntimeException {
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("METHOD NOT ALLOWED");
        }
        return userId;
    }


    public static ProfileJwtDto getProfile(HttpServletRequest request, ProfileRole requiredRole) {
        ProfileJwtDto jwtDTO = (ProfileJwtDto) request.getAttribute("jwtDTO");
        if (jwtDTO == null) {
            throw new UnauthorizedException("Not authorized");
        }

        if (!jwtDTO.getRole().equals(requiredRole)) {
            throw new ForbiddenException("Forbidden exp");
        }
        return jwtDTO;
    }

}
