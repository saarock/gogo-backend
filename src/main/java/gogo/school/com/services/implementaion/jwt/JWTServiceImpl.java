package gogo.school.com.services.implementaion.jwt;

import java.util.Date;
import org.springframework.stereotype.Service;
import gogo.school.com.services.JWTService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTServiceImpl implements JWTService {


    private static final String SECRET_KEY = "dnfgasjkdgbasjgbasdgbas";
    private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60; // 5 HOUR
    private static final long REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 60; // 10 HOUR

    @Override
    public String generateAccessToken(int id) {
        return Jwts.builder()
                .setSubject(String.valueOf(id))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    @Override
    public String generateRefreshToken(int id) {
        return Jwts.builder()
                .setSubject(String.valueOf(id))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_SECONDS * 1000))
                
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

}
