package cs590.accountservice.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${SECRET_KEY}")
    private String secret_key;

    public String getUserName(String token){
        return Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret_key)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.out.println(e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println(e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println(e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }


//    public Boolean validateToken(String token){
//        final String username = extractUsername(token);
//        return !isTokenExpired(token);
//    }

}
