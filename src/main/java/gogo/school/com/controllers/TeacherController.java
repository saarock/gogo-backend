package gogo.school.com.controllers;

import org.springframework.web.bind.annotation.RestController;

import gogo.school.com.models.BatchesModel;
import gogo.school.com.models.SchoolModel;
import gogo.school.com.models.TeacherModel;
import gogo.school.com.services.EmailService;
import gogo.school.com.services.JWTService;
import gogo.school.com.services.SchoolService;
import gogo.school.com.services.Security;
import gogo.school.com.services.TeacherService;
import gogo.school.com.utils.OTPGenerator;
import gogo.school.com.utils.OTPStorage;
import gogo.school.com.utils.ResponseMessage;
import gogo.school.com.utils.ResponseToken;
import gogo.school.com.utils.VerifyOTP;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;
import java.util.Set;

import javax.swing.text.html.Option;

import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class TeacherController {

    static class RequestWrapper {
        private TeacherModel teacherModel;
        private String enteredOTP;

        public TeacherModel getTeacherModel() {
            return teacherModel;
        }

        public void setTeacherModel(TeacherModel teacherModel) {
            this.teacherModel = teacherModel;
        }

        public String getEnteredOTP() {
            return enteredOTP;
        }

        public void setEnteredOTP(String enteredOTP) {
            this.enteredOTP = enteredOTP;
        }

    }

    @Setter
    @Getter
    @NoArgsConstructor
    static class TeacherCreationResponse {
        private ResponseMessage<TeacherModel> teacherResponse;
        private ResponseToken refreshToken;

        public TeacherCreationResponse(ResponseMessage<TeacherModel> teacherResponse, ResponseToken refreshToken) {
            this.teacherResponse = teacherResponse;
            this.refreshToken = refreshToken;
        }

        // Getters and setters for teacherResponse and refreshToken
    }

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OTPStorage otpStorage;

    @Autowired
    private VerifyOTP verifyOTP;

    @Autowired
    private Security security;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ResponseToken responseToken;

    @PostMapping("teacher/create_new_teacher")
    public ResponseMessage<TeacherModel> postMethodName(@RequestBody RequestWrapper requestWrapper) {
        try {
            if (requestWrapper == null) {
                return new ResponseMessage<>("not-success", 403, "Details required!", null, null);

            }

            /**
             * Checked the every data is empty or not correct or valid or not;
             */
            TeacherModel teacherModel = requestWrapper.getTeacherModel();
            if (teacherModel == null) {
                return new ResponseMessage<>("not-success", 403, "Teacher Details is Required", null, null);
            }

            if (teacherModel.getTeacherName() == null || teacherModel.getTeacherName().isEmpty()) {
                System.out.println("Teacher name is missing or empty");
                return new ResponseMessage<>("not-success", 401, "Teacher name is missing or empty", null, null);
            }

            if (teacherModel.getTeacherAddress() == null || teacherModel.getTeacherAddress().isEmpty()) {
                System.out.println("Teacher address is missing or empty");
                return new ResponseMessage<>("not-success", 401, "Teacher address is missing or empty", null, null);
            }

            if (teacherModel.getTeacherMail() == null || teacherModel.getTeacherMail().isEmpty()) {
                System.out.println("Teacher mail is missing or empty");
                return new ResponseMessage<>("not-success", 401, "Teacher mail is missing or empty", null, null);
            }

            if (teacherModel.getTeacherPassword() == null || teacherModel.getTeacherPassword().isEmpty()) {
                System.out.println("Teacher password is missing or empty");
                return new ResponseMessage<>("not-success", 401, "Teacher password is missing or empty", null, null);
            }

            /**
             * Reteriving the schoolmodel and verifying
             */
            int schoolId = teacherModel.getSchoolModel().getId();
            /**
             * check here that school has any data or not;
             */
            SchoolModel schoolModel = schoolService.findById(schoolId);

            // return thet not-succes message if the school doesnot exist;
            if (schoolModel == null) {
                return new ResponseMessage<>("not-success", 401, "Invalid school id is given", null, null);
            }

            teacherModel.setSchoolModel(schoolModel);

            // hashing the password
            String hashPassword = security.hashPassword(teacherModel.getTeacherPassword());
            teacherModel.setTeacherPassword(hashPassword);

            if (hashPassword.isEmpty() || hashPassword == null) {
                return new ResponseMessage<>("not-success", 500, "SOmething wrong while hashin the password", null,
                        null);
            }

            String enteredOTP = requestWrapper.getEnteredOTP().trim();

            if (enteredOTP.length() > 6) {
                return new ResponseMessage<>("not-success", 401, "Invalid otp (Lenght is greater than 6)", null, null);
            }

            // check the provide otp contains the string if yes return not-success message

            char[] otpChar = enteredOTP.toCharArray();
            for (int i = 0; i < otpChar.length; i++) {
                if (!Character.isDigit(otpChar[i])) {
                    return new ResponseMessage<>("not-success", 401, "Invalid otp (Getting invalid Character)", null,
                            null);

                }
            }

            long otpAsLong = Long.parseLong(enteredOTP);
            System.out.println(otpAsLong + "haha" + teacherModel.getTeacherMail());
            boolean isVerified = verifyOTP.verifyOtp(teacherModel.getTeacherMail().trim().toLowerCase(), otpAsLong);
            if (isVerified) {

                /*
                 * Generate the access and the refereshToken beforing savind to the database
                 */
                String accessToken = jwtService.generateAccessToken(teacherModel.getId());
                String refereshToken = jwtService.generateRefreshToken(teacherModel.getId());

                /**
                 * setting the refresh and the access token and send to the client
                 */

                responseToken.setAccessToken(accessToken);
                responseToken.setRefreshToken(refereshToken);

                if (accessToken.isEmpty() || refereshToken.isEmpty()) {
                    return new ResponseMessage<>("not-success", 400, "Pleased try again with the valid data", null,
                            responseToken);
                }

                teacherModel.setTeacherRefershToken(refereshToken);

                // create the new teacher on the database
                TeacherModel teacherData = teacherService.createNewTeacher(teacherModel);

                if (teacherData != null) {
                    return new ResponseMessage<>("success", 200, "School added Success", teacherData, responseToken);
                } else {
                    return new ResponseMessage<>("not-success", 500,
                            "Some things wrong pleased try again by refershing the page", null, null);
                }
            } else {
                return new ResponseMessage<>("not-success", 400, "OTP expired", null, null);
            }
        } catch (Exception error) {
            return new ResponseMessage<>("error", 400, "Some thing occurs", null, null);
        }
    }

    /**
     * Generate the Otp
     * 
     * @param teacherMail
     * @return
     */
    @PostMapping("teacher/save_teacher_data_tempo_and_send_otp")
    public ResponseMessage<String> generateOTP(@RequestBody String teacherMail) {
        try {
            System.out.println(teacherMail);

            // CHeck the email is proper or not;
            if (teacherMail.isEmpty() || teacherMail == null) {
                return new ResponseMessage<>("not-success", 403, "Email is required!", null, null);

            }

            // Generate a new OTP
            Long otp = OTPGenerator.generateOTP();
            // Sending the email with OTP to the school's email address
            boolean doesMailSent = emailService.sendEmail(teacherMail.trim().toLowerCase(),
                    "Verify Your Email Address",
                    "Your OTP is: " + otp);

            if (doesMailSent) {
                otpStorage.storeOTP(teacherMail, otp);
                return new ResponseMessage<>("success", 200, "OTP is sendSuccessfull pleased check the email", null,
                        null);

            } else {

                return new ResponseMessage<>("not-success", 200,
                        "Something pleased try again if any issues arrives contact us.", null, null);
            }
        } catch (Exception error) {
            return new ResponseMessage<>("failed", 0, error.getMessage(), null, null);
        }

    }

    /**
     * 
     * @param model
     * @return
     */

    @Setter
    @Getter
    @NoArgsConstructor
    static class LoginRequest {
        private String email;
        private String password;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("teacher/login")
    public ResponseMessage<TeacherModel> loginTeacher(@RequestBody LoginRequest data) {
        /**
         * algorithm to generate the login teacher
         */
     
        if ( data.getEmail() == null || data.getEmail().isEmpty()) {
            return new ResponseMessage<>("not-success", 500, "Email required", null, null);
        }

        System.out.println(data.getEmail()+ "THis is theemail");
        if (data.getPassword() == null || data.getPassword().isEmpty()) {
            return new ResponseMessage<>("not-success", 500, "Password required", null, null);
        }
        System.out.println(data.getPassword() + "this is the password");
        Optional<TeacherModel> teacherData = teacherService.findTeacherByEmail(data.email.toLowerCase().trim());
        if (!teacherData.isPresent()) {
            return new ResponseMessage<>("not-success", 500, "Wrong email", null, null);
        }
        TeacherModel teacher = teacherData.get();
        if (!teacher.isActive()) {
            return new ResponseMessage<>("not-success", 500, "Sorry your are not able to login contact to your school admin", null, null);
        }
        String encodedPassword = teacher.getTeacherPassword();
        boolean doesPasswordMatch = security.verifyPassword(data.password, encodedPassword);
        if (!doesPasswordMatch) {
            return new ResponseMessage<>("not-success", 500, "Wrong password", null, null);

        }

        // Generat the new token
        String accessToken = jwtService.generateAccessToken(teacher.getId());
        String refereshToken = jwtService.generateRefreshToken(teacher.getId());
        if (accessToken.isEmpty() || accessToken == null || refereshToken.isEmpty() || refereshToken == null) {

            return new ResponseMessage<>("not-success", 500, "Pleased try agian if any things happend contact us!",
                    null, null);
        }
        
        teacher.setTeacherRefershToken(refereshToken);
        /**
         * Updating the refereshToken
         */
        teacherService.createNewTeacher(teacher);

        // teacher.setTeacherPassword(null);
        responseToken.setAccessToken(accessToken);
        responseToken.setRefreshToken(refereshToken);

        teacher.setTeacherPassword(null);
        teacher.setTeacherRefershToken(null);
        

        // teacher.getSchoolModel().getBatches();
    
        return new ResponseMessage<>("success", 200, "Login successfull", teacher, responseToken);

        // return teacher;

    }







    


    /**
     * @param tokenRequestWrapper
     * @return Boolean value if the accessToken is valid;
     */


    @Setter
    @Getter
    @NoArgsConstructor   
    static class accessTokenRequestWrapper {
        private String accessToken;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/verify_teacher/cookie")
    public boolean verifyAccessToken(@RequestBody accessTokenRequestWrapper tokenRequestWrapper) {
        return true;
    }

}
