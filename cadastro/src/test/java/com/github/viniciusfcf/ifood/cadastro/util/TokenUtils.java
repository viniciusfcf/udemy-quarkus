package com.github.viniciusfcf.ifood.cadastro.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;

/**
 * Utilities for generating a JWT for testing
 * 
 * Peguei nesse endereço
 * https://quarkus.io/guides/security-jwt
 */
public class TokenUtils {

    private TokenUtils() {
        // no-op: utility class
    }

    /**
     * Utility method to generate a JWT string from a JSON resource file that is signed by the privateKey.pem
     * test resource key, possibly with invalid fields.
     *
     * @param jsonResName - name of test resources file
     * @param timeClaims - used to return the exp, iat, auth_time claims
     * @return the JWT string
     * @throws Exception on parse failure
     */
    public static String generateTokenString(final String jsonResName, final Map<String, Long> timeClaims)
            throws Exception {
        // Use the test private key associated with the test public key for a valid signature
        final PrivateKey pk = readPrivateKey("/privateKey.pem");
        return generateTokenString(pk, "/privateKey.pem", jsonResName, timeClaims);
    }

    public static String generateTokenString(final PrivateKey privateKey, final String kid,
            final String jsonResName, final Map<String, Long> timeClaims) throws Exception {

        final JwtClaims claims = JwtClaims.parse(readTokenContent(jsonResName));
        final long currentTimeInSecs = currentTimeInSecs();
        final long exp = timeClaims != null && timeClaims.containsKey(Claims.exp.name())
                ? timeClaims.get(Claims.exp.name())
                : currentTimeInSecs + 300;

        claims.setIssuedAt(NumericDate.fromSeconds(currentTimeInSecs));
        claims.setClaim(Claims.auth_time.name(), NumericDate.fromSeconds(currentTimeInSecs));
        claims.setExpirationTime(NumericDate.fromSeconds(exp));

        for (final Map.Entry<String, Object> entry : claims.getClaimsMap().entrySet()) {
            System.out.printf("\tAdded claim: %s, value: %s\n", entry.getKey(), entry.getValue());
        }

        final JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(privateKey);
        jws.setKeyIdHeaderValue(kid);
        jws.setHeader("typ", "JWT");
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        return jws.getCompactSerialization();
    }

    private static String readTokenContent(final String jsonResName) throws IOException {
        final InputStream contentIS = TokenUtils.class.getResourceAsStream(jsonResName);
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(contentIS))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

    /**
     * Read a PEM encoded private key from the classpath
     *
     * @param pemResName - key file resource name
     * @return PrivateKey
     * @throws Exception on decode failure
     */
    public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
        final InputStream contentIS = TokenUtils.class.getResourceAsStream(pemResName);
        final byte[] tmp = new byte[4096];
        final int length = contentIS.read(tmp);
        return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
    }

    /**
     * Decode a PEM encoded private key string to an RSA PrivateKey
     *
     * @param pemEncoded - PEM string for private key
     * @return PrivateKey
     * @throws Exception on decode failure
     */
    public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        final byte[] encodedBytes = toEncodedBytes(pemEncoded);

        final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        final KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    private static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    private static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    /**
     * @return the current time in seconds since epoch
     */
    public static int currentTimeInSecs() {
        final long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }

}
