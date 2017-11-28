package global.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;
import java.util.UUID;

final public class JwtUtility {

    private static String apiKey = UUID.randomUUID().toString();

    public static String createToken(String id, String issuer, String subject, Map<String,Object> claim) {

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuer(issuer)
                .addClaims(claim)
                .signWith(SignatureAlgorithm.HS256, signingKey);

        return builder.compact();
    }

    public static Claims parseToken(String jwt) {

        Claims parsedValue = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(apiKey))
                .parseClaimsJws(jwt).getBody();

        System.out.println("ID: " + parsedValue.getId());
        System.out.println("Subject: " + parsedValue.getSubject());
        System.out.println("Issuer: " + parsedValue.getIssuer());

        return parsedValue;
    }

}
