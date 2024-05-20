package gogo.school.com.services.implementaion.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gogo.school.com.config.WebSecurityConfig;
import gogo.school.com.services.Security;

@Service
public class SecurityImpl implements Security {

    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Override
    public String hashPassword(String password) {
        return webSecurityConfig.passwordEncoder().encode(password);

    }

    @Override
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return webSecurityConfig.passwordEncoder().matches(rawPassword, encodedPassword);
    }

}
