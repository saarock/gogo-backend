package gogo.school.com.utils;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class OTPGenerator {

    public static long generateOTP() {
        // Define the length of the OTP
        int length = 6;

        // Define the characters from which the OTP will be composed
        String numbers = "0123456789";

        // Use a StringBuilder to store the OTP
        StringBuilder otp = new StringBuilder();

        // Use a random number generator to pick characters from the numbers string
        Random random = new Random();

        // Generate the OTP by appending random characters from the numbers string
        for (int i = 0; i < length; i++) {
            otp.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        // Convert the OTP string to a long
        long generatedOTP = Long.parseLong(otp.toString());
        return generatedOTP;
    }
}
