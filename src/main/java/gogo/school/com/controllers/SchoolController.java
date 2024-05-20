package gogo.school.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import gogo.school.com.config.WebSecurityConfig;
import gogo.school.com.models.SchoolModel;
import gogo.school.com.services.SchoolService;
import gogo.school.com.services.Security;
import gogo.school.com.utils.OTPGenerator;
import gogo.school.com.utils.OTPStorage;
import gogo.school.com.utils.ResponseMessage;
import gogo.school.com.utils.VerifyOTP;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import gogo.school.com.services.EmailService;

@RestController
public class SchoolController {

    /**
     * Request body takes this class as s Request;
     */
    static class RequestWrapper {
        private SchoolModel schoolModel;
        private String enteredOTP;

        public SchoolModel getSchoolModel() {
            return schoolModel;
        }

        public void setSchoolModel(SchoolModel schoolModel) {
            this.schoolModel = schoolModel;
        }

        public String getEnteredOTP() {
            return enteredOTP;
        }

        public void setEnteredOTP(String enteredOTP) {
            this.enteredOTP = enteredOTP;
        }

    }

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerifyOTP verifyOTP;

    @Autowired
    private OTPStorage otpStorage;

    /**
     * @param requestWrapper
     * @return ResponseMessage which is the object if all the things were right if
     *         not then also
     *         return the ResponseMessage with the non-sucess message
     */
    @PostMapping("/verify_otp")
    public ResponseMessage<String> checkOTPIfCorrectSaveNewSchool(@RequestBody RequestWrapper requestWrapper) {

        try {

            SchoolModel schoolModel = requestWrapper.getSchoolModel();
            if (schoolModel == null) {
                return new ResponseMessage<>("not-success", 403, "SchoolDetails is Required", null, null);

            }

            // Checked if every data is empty or not correct or valid or not;
            if (schoolModel.getSchoolName() == null || schoolModel.getSchoolName().isEmpty()) {
                return new ResponseMessage<>("not-success", 403, "SchoolName is Required", null, null);
            }

            if (schoolModel.getSchoolAddress() == null || schoolModel.getSchoolAddress().isEmpty()) {
                return new ResponseMessage<>("not-success", 403, "SchoolAddress is Required", null, null);
            }

            if (schoolModel.getSchoolEmail() == null || schoolModel.getSchoolEmail().isEmpty()) {
                return new ResponseMessage<>("not-success", 403, "SchoolEmail is Required", null, null);
            }

            if (schoolModel.getSchoolPhoneNumber() == null || schoolModel.getSchoolPhoneNumber().isEmpty()) {
                return new ResponseMessage<>("not-success", 403, "SchoolPhoneNumber is Required", null, null);
            }

            // If all checks pass, continue with your logic to save the school or perform
            // further validations
            String enteredOTP = requestWrapper.getEnteredOTP().trim();
            if (enteredOTP.isEmpty() || enteredOTP == null) {
                return new ResponseMessage<>("not-success", 403, "Pleased Provide the OTP", null, null);

            }

            if (enteredOTP.length() > 6) {
                return new ResponseMessage<>("not-success", 403, "Invalid otp (Lenght is greater than 6)", null, null);
            }

            // check the provide otp contains the string if yes return not-success message
            char[] otpChar = enteredOTP.toCharArray();

            for (int i = 0; i < otpChar.length; i++) {
                if (!Character.isDigit(otpChar[i])) {
                    return new ResponseMessage<>("not-success", 403, "Invalid otp (Getting invalid Character)", null,
                            null);
                }
            }

            long otpAsLong = Long.parseLong(enteredOTP);
            boolean isVerified = verifyOTP.verifyOtp(schoolModel.getSchoolEmail().trim().toLowerCase(), otpAsLong);

            if (!isVerified) {
                return new ResponseMessage<>("not-success", 400, "OTP is incorrect.", null, null);
            }
            
            boolean isSchoolDetailsSaved = schoolService.saveNewSchool(schoolModel);
            if (!isSchoolDetailsSaved) {
                return new ResponseMessage<>("not-success", 500, "Pleased try again.", null, null);

            }
            // if all right resnd the success respone to the admin for futher works
            return new ResponseMessage<>("success", 200, "School added Success", null, null);

        } catch (Exception error) {
            return new ResponseMessage<>("error", 400, "Some thing occurs", null, null);
        }
    }

    /**
     * @param schoolMail
     * @return ResponseMessage which is the object if all the things were right if
     *         not then also
     *         return the ResponseMessage with the non-sucess message
     */
    @PostMapping("/save_school_data_tempo_and_send_otp")
    public ResponseMessage<String> generateOTP(@RequestBody String schoolMail) {
        try {
            if (schoolMail.isEmpty() || schoolMail == null || schoolMail == "") {
                return new ResponseMessage<>("not-success", 403, "Pleased provide the email", null, null);
            }

            boolean doesEmailExist = schoolService.checkEmailExistOrNot(schoolMail);
            if (doesEmailExist) {
                return new ResponseMessage<>("not-success", 403,
                        "Email already exist pleased go through the login page.", null, null);
            }

            // Generate a new OTP
            Long otp = OTPGenerator.generateOTP();

            // Sending the email with OTP to the school's email address
            boolean doesMailSent = emailService.sendEmail(schoolMail.trim().toLowerCase(),
                    "Verify Your Email Address",
                    "Your OTP is: " + otp);

            if (doesMailSent) {
                otpStorage.storeOTP(schoolMail, otp);
                return new ResponseMessage<>("success", 200, "OTP is sendSuccessfull pleased check the email", null,
                        null);
            } else {
                return new ResponseMessage<>("not-success", 500,
                        "Something pleased try again if any issues arrives contact us.", null, null);
            }
        } catch (Exception error) {
            return new ResponseMessage<>("failed", 0, error.getMessage(), null, null);
        }

    }

    /**
     * 
     * @param a
     * @return resposne message
     */
    public ResponseMessage<String> loginTeacher(@RequestBody String a) {
        return null;
    }

    public ResponseMessage<String> verifyAccessToken(@RequestBody String a) {
        return null;

    }

    public ResponseMessage<String> verifyRefereshToken(@RequestBody String a) {
        return null;
    }

}
