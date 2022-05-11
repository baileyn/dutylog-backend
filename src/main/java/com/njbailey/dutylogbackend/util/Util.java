package com.njbailey.dutylogbackend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.njbailey.dutylogbackend.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public final class Util {
    private static final String SECRET = "ThisTrulyIsntSecret";
    private static final int HASH_WORKLOAD = 11;

    private Util() {
    }

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(Util.HASH_WORKLOAD);
        return BCrypt.hashpw(password, salt);
    }

    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        if (plainTextPassword == null || hashedPassword == null) {
            return false;
        }

        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    public static String generateTokenForUser(User user) {
        Algorithm algorithm = Algorithm.HMAC256(Util.SECRET);
        return JWT.create()
                .withIssuer("DutyLog")
                .withClaim("userid", user.getId())
                .sign(algorithm);
    }

    public static Optional<DecodedJWT> verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(Util.SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("DutyLog")
                    .build(); //Reusable verifier instance
            return Optional.of(verifier.verify(token));
        } catch (JWTVerificationException e) {
            System.out.println(e);
            return Optional.empty();
        }
    }
}
