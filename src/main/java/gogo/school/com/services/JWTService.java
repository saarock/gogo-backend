package gogo.school.com.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public interface JWTService {  
    public String  generateRefreshToken(int id);
    public String generateAccessToken(int id);
}
