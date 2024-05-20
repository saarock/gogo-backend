package gogo.school.com.utils;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Setter
@Getter
@NoArgsConstructor
public class ResponseToken {

    private String accessToken;
    private String refreshToken;

    public ResponseToken(String accessToken, String refereToken) {
        this.accessToken = accessToken;
        this.refreshToken = refereToken;
    }
}
