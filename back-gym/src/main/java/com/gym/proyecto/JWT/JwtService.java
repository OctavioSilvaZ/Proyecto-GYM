package com.gym.proyecto.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.services.VariableGlobalService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    @Autowired
    private VariableGlobalService variableGlobalService;

    private SecretKey key;

    // inicia la función para llamar a la llave
    private void initKey() {
        String secret = variableGlobalService.buscarId((int) 1).getValor();
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // genera el token
    public String generateToken(String correo) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, correo);
    }

    // Crea el token
    public String createToken(Map<String, Object> claims, String correo) {
        initKey();
        return Jwts.builder()
                .claims(claims)
                .subject(correo)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(key, Jwts.SIG.HS512) // Nueva forma recomendada
                .compact();
    }

    // Extrae el correo del token
    public String extractCorreo(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Verifica si el token ha expirado
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // valida que el token no este expirado y que el correo extraido sea el de un
    // personal
    public Boolean validateToken(String token, PersonalModel userDetails) {
        final String username = extractCorreo(token);
        return (username.equals(userDetails.getCorreo()) && !isTokenExpired(token));
    }

}
