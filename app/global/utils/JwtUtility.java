package global.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;

final public class JwtUtility {

    public JwtUtility() {
    }

    private static String apiKey = "secret";

    public static String createToken(String id, Map<String,Object> claim) {

        if (apiKey !=  null) {
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

            JwtBuilder builder = Jwts.builder()
                    .setId(id)
                    .addClaims(claim)
                    .signWith(SignatureAlgorithm.HS256, signingKey);

            return builder.compact();
        }

        return null;

    }

    public static Claims parseToken(String jwt) {

        Claims parsedValue = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(apiKey))
                .parseClaimsJws(jwt).getBody();

        System.out.println("ID: " + parsedValue.getId());

        return parsedValue;
    }

}
