package rest.api.cardinity.taskmanager.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author dipanjal
 * @since 2/7/2021
 */
public class JWTUtils {

    private final static Environment env = ApplicationContextHolder.getContext().getEnvironment();


    /* PUBLIC METHODS */

    public static String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    public static String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public static Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public static String trimToken(String barerToken){
        String tokenPrefix = String.format("%s ", getJWTTokenPrefix());
        return StringUtils.replace(barerToken, tokenPrefix, "");
    }

    public static boolean validateToken(String token, UserDetails userDetails){
        return (
                StringUtils.equals(extractUserName(token), userDetails.getUsername())
                && !isTokenExpired(token)
        );
    }

    /* PRIVATES */

    private static String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + getExpiryMilli()))
                .signWith(SignatureAlgorithm.HS256, getJWTSecretKey())
                .compact();
    }

    private static int getExpiryMilli(){
        String expiryTimeString = env.getProperty("jwt.token.expirey.minute");
        int expMinutes = Integer.valueOf(expiryTimeString);
        return DateTimeUtils.convertToMilli(expMinutes, Calendar.MINUTE);
    }

    private static String getJWTSecretKey(){
        return env.getProperty("jwt.secret.key");
    }

    private static String getJWTTokenPrefix(){
        return env.getProperty("jwt.token.prefix");
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getJWTSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private static boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
}
