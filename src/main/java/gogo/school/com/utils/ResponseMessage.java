package gogo.school.com.utils;

import org.springframework.beans.factory.annotation.Autowired;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMessage<T> {
    private String type;
    private int status;
    private String message;
    private T data;


    @Autowired
    private ResponseToken tokens;

    public ResponseMessage(String type, int status, String message, T data, ResponseToken tokens) {
        this.type = type;
        this.status = status;
        this.message = message;
        this.data = data;
        this.tokens = tokens;
    }


}
