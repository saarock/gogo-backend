package gogo.school.com.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class OTPStorage {

    private static Map<String, Long> otpMap = new HashMap<>();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void storeOTP(String key, Long timestamp) {
        otpMap.put(key, timestamp);

        // remove automatically after one minutes
        // scheduler.schedule(() -> otpMap.remove(key), 1, TimeUnit.MINUTES);
    }

    public Long getStoredOTP(String key) {
        return otpMap.get(key);
    }

    public void removeOTP(String key) {
        otpMap.remove(key);
    }

}
