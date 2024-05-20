package gogo.school.com.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class VerifyOTP {

     @Autowired
     OTPStorage otpStorage;
     
    // Verify the entered OTP
    public boolean verifyOtp(String email, Long enteredOtp) {
        Long storedOtp = otpStorage.getStoredOTP(email);
        if (storedOtp != null && storedOtp.equals(enteredOtp)) {
            // Clear OTP after successful verification
            // otpStorage.removeOTP(email);
            return true; // OTP is correct
        } else {
            return false; // OTP is incorrect
        }
    }
}
