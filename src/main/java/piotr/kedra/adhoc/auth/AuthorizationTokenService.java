package piotr.kedra.adhoc.auth;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import piotr.kedra.adhoc.auth.entity.User;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Random;

@Component
public class AuthorizationTokenService {

    private Algorithm algorithm;

    public AuthorizationTokenService() {
        String secret = generateRandomSecret();
        System.out.println(secret);
        algorithm = Algorithm.HMAC256(secret);
    }


    private String generateRandomSecret() {
        byte[] array = new byte[15]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    public String generateToken(User user) throws JWTCreationException {
        return JWT.create()
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("name", user.getName())
                .sign(algorithm);
    }

    public Optional<User> verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT result = verifier.verify(token);
            Claim idClaim = result.getClaim("id");
            Claim emailClaim = result.getClaim("email");
            Claim nameClaim = result.getClaim("name");
            Long id = idClaim.asLong();
            String email = emailClaim.asString();
            String name = nameClaim.asString();
            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setName(name);
            System.out.println("OK");
            return Optional.of(user);
        }catch (JWTVerificationException exp){
            return Optional.empty();
        }
    }
}
