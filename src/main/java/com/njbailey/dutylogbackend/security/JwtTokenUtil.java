package com.njbailey.dutylogbackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenUtil implements Serializable {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Algorithm algorithm;
    private JWTVerifier verifier;

    private boolean ignoreTokenExpiration(DecodedJWT token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(UserDetails userDetails) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(userDetails.getUsername())
                .withIssuedAt(createdDate)
                .withExpiresAt(expirationDate)
                .sign(getAlgorithm());
    }

    public Boolean canTokenBeRefreshed(DecodedJWT token, Date lastPasswordReset) {
        final Date created = token.getIssuedAt();
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public boolean validateToken(DecodedJWT token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = token.getSubject();
        final Date created = token.getIssuedAt();

        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())
        );
    }

    public Optional<DecodedJWT> getDecodedJWT(String token) {
        try {
            return Optional.of(getVerifier().verify(token));
        } catch (JWTVerificationException e) {
            System.out.println("Decode Failure: " + e.getMessage());
            return Optional.empty();
        }
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }

    private boolean isTokenExpired(DecodedJWT token) {
        final Date expiration = token.getExpiresAt();
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Algorithm getAlgorithm() {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC256(secret);
        }

        return algorithm;
    }

    private JWTVerifier getVerifier() {
        if (verifier == null) {
            verifier = JWT.require(getAlgorithm()).withIssuer(issuer).build();
        }

        return verifier;
    }
}