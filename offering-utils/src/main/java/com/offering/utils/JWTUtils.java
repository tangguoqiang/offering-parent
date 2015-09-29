package com.offering.utils;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

/**
 * json web token共通处理类
 * @author surfacepro3
 *
 */
public class JWTUtils {
	
	// 生成一对 RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
	private static RsaJsonWebKey rsaJsonWebKey = null;
	
	static{
		try {
			rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
			// Give the JWK a Key ID (kid), which is just the polite thing to do
		    rsaJsonWebKey.setKeyId("offering");
		} catch (JoseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生产token
	 * @return
	 * @throws JoseException 
	 */
	public static String generateToken(String userId) throws JoseException
	{
	    // Create the Claims, which will be the content of the JWT
	    JwtClaims claims = new JwtClaims();
	    claims.setIssuer("Issuer");  // who creates the token and signs it
	    claims.setAudience("Audience"); // to whom the token is intended to be sent
	    claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
	    claims.setGeneratedJwtId(); // a unique identifier for the token
	    claims.setIssuedAtToNow();  // when the token was issued/created (now)
	    claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
	    claims.setSubject("user"); // the subject/principal is whom the token is about
	    claims.setClaim("id",userId); // additional claims/attributes about the subject can be added
//	    List<String> groups = Arrays.asList("group-one", "other-group", "group-three");
//	    claims.setStringListClaim("groups", groups); // multi-valued claims work too and will end up as a JSON array

	    // A JWT is a JWS and/or a JWE with JSON claims as the payload.
	    JsonWebSignature jws = new JsonWebSignature();
	    jws.setPayload(claims.toJson());
	    jws.setKey(rsaJsonWebKey.getPrivateKey());
	    jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
	    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
	    return jws.getCompactSerialization();
	}
	
	/**
	 * 校验token
	 * @param userId
	 * @param token
	 * @return
	 */
	public static boolean checkToken(String userId,String token)
	{
		// Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
	    // be used to validate and process the JWT.
	    // The specific validation requirements for a JWT are context dependent, however,
	    // it typically advisable to require a expiration time, a trusted issuer, and
	    // and audience that identifies your system as the intended recipient.
	    // If the JWT is encrypted too, you need only provide a decryption key or
	    // decryption key resolver to the builder.
	    JwtConsumer jwtConsumer = new JwtConsumerBuilder()
	            .setRequireExpirationTime() // the JWT must have an expiration time
	            .setAllowedClockSkewInSeconds(5) // allow some leeway in validating time based claims to account for clock skew
	            .setRequireSubject() // the JWT must have a subject claim
	            .setExpectedIssuer("Issuer") // whom the JWT needs to have been issued by
	            .setExpectedAudience("Audience") // to whom the JWT is intended for
	            .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
	            .build(); // create the JwtConsumer instance

	    try
	    {
	        //  Validate the JWT and process it to the Claims
	        JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
	        System.out.println(jwtClaims.getClaimValue("id"));
	        System.out.println("JWT validation succeeded! " + jwtClaims);
	        return true;
	    }
	    catch (InvalidJwtException e)
	    {
	        System.out.println("Invalid JWT! " + e);
	    }
	    return false;
	}
	
	public static void main(String[] args) {
		String userId = "1";
		try {
			String token = generateToken(userId);
			System.out.println(token);
			System.out.println(checkToken(userId, token));
		} catch (JoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
