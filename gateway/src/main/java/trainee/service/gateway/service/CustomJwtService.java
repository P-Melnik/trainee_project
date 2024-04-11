package trainee.service.gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Service
public class CustomJwtService {

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    public boolean isTokenValid(String header) {
        boolean isValid = false;
        if (StringUtils.isEmpty(header) || !StringUtils.startsWith(header, "Bearer ")) {
            log.info("Token not found in the header");
        } else {
            String jwt = header.substring(7);
            try {
                Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(jwt);
                isValid = !isTokenExpired(jwt);
                if (isValid) {
                    log.info("Token is valid");
                } else {
                    log.info("Token is expired");
                }
            } catch (SignatureException e) {
                log.error("Invalid token secret key: {}", e.getMessage());
            } catch (JwtException e) {
                log.error("Invalid token: {}", e.getMessage());
            }
        }
        return isValid;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
