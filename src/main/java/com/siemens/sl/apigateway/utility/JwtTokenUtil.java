package com.siemens.sl.apigateway.utility;

import com.siemens.sl.apigateway.exception.customexceptions.GenericException;
import com.siemens.sl.apigateway.model.User;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    //TODO - MOVE THIS TO APPLICATION FILE
    private static final long ACCESS_TOKEN_VALIDITY = 86400;  //1 day
//    private static final long REFRESH_TOKEN_VALIDITY = 3600000; //60000 mins

    private static Logger logger = LogManager.getLogger(JwtTokenUtil.class);

    @Value("jwt.access_token_secret")
    private String accessTokenSecret;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //Returns all the claims, attributes from the JWT
    private Claims getAllClaimsFromToken(String token) {
//        logger.error("AccessTokenSecret : "+accessTokenSecret);
        return Jwts.parser().setSigningKey(accessTokenSecret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }

    /**
     * This method is used to generate access token
     *
     * @param user
     * @return
     */
    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        addClaims(user, claims);
        return doGenerateToken(claims, user.getUserName());
    }

//    /**
//     * This method is used to generate refresh token for the user
//     *
//     * @param user
//     * @return
//     */
//    public String generateRefreshToken(User user) {
//        Map<String, Object> claims = new HashMap<>();
//        addClaims(user, claims);
//        return doGenerateToken(claims, user.getUserName(), true);
//    }

    /**
     * This method is used to add claims to the JWT token
     *
     * @param user   - Pass the user object here
     * @param claims - Pass the claims hashmap to which claims need to be added
     */
    private void addClaims(User user, Map<String, Object> claims) {
        //TODO - HOW TO ADD, CHECK IF WE CAN REMOVE IT
        if (user.getRoles() != null && user.getRoles().length > 0) {
            claims.put("role", user.getRoles()[0].getName());
        }
    }

    /**
     * IMP - THIS METHOD IS NOT BEING USED CURRENTLY- KEPT FOR REFRESH TOKEN FUNCTIONALITY
     *
     * while creating the token -
     *     1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
     *     2. Sign the JWT using the HS512 algorithm and secret key.
     *     3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
     *        compaction of the JWT to a URL-safe string
     */

//    private String doGenerateToken(Map<String, Object> claims, String subject, boolean isRefreshToken) {
////        System.out.println("JWT SECRET = " + accessTokenSecret);
//        JwtBuilder token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()));
//
//        if (isRefreshToken) {
//            return token.setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY * 1000))
//                    .signWith(SignatureAlgorithm.HS512, accessTokenSecret).compact();
//        } else {
//            return token.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY * 1000))
//                    .signWith(SignatureAlgorithm.HS512, accessTokenSecret).compact();
//        }
//    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        JwtBuilder token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()));
        return token.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, accessTokenSecret).compact();

    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * This method is used to check weather a request contains access-token or not, if not it throws an exception
     *
     * @param request - Pass the request here
     * @return - Access token without bearer
     */
    public String getAccessTokenFromRequest(HttpServletRequest request) {
        final String accessTokenHeader = request.getHeader("Authorization");
        if (accessTokenHeader == null) {
            //The request does not contain any token
            logger.error("Bearer token not found");
            throw new GenericException(HttpStatus.BAD_REQUEST, 400, 2, null);
        } else if (!accessTokenHeader.startsWith("Bearer ")) {
            //The request contains token but the token does not start with 'Bearer'
            logger.error("Bearer token does not start with 'Bearer'");
            throw new GenericException(HttpStatus.BAD_REQUEST, 401, 12, null);
        }
        return accessTokenHeader.substring(7);
    }
}
