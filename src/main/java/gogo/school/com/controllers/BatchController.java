package gogo.school.com.controllers;

import  java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import gogo.school.com.models.BatchesModel;
import gogo.school.com.models.SchoolModel;
import gogo.school.com.models.TeacherModel;
import gogo.school.com.services.BatchService;
import gogo.school.com.services.SchoolService;
import gogo.school.com.services.TeacherService;
import gogo.school.com.utils.ResponseMessage;
import jakarta.mail.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BatchController
 */
@RestController
public class BatchController {

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private BatchService batchService;
    @Autowired
    private TeacherService teacherService;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/create_new_batch")
    public ResponseMessage<String> createNewBatch(@RequestBody BatchesModel batch) {
        try {

            if (batch == null) {
                return new ResponseMessage<>("not-success", 403, "Batch is NUll", null, null);
            }

            if (teacherService.isEmpty()) {
                return new ResponseMessage<>("not-success", 403, "Teacher doesnot exist.", null, null);
            }
            // batch.getTeacher()
            // Finding the school from the school id which is receving from batch details
            int schoolId = batch.getSchoolOfTheNepal().getId();
            SchoolModel school = schoolService.findById(schoolId);

            if (school == null) {
                return new ResponseMessage<>("not-success", 403, "School doensot exist", null, null);
            }


            int teacherId = batch.getTeacherOfSchool().getId();
            TeacherModel teacher = teacherService.findById(teacherId);
            if (teacher == null) {
                return new ResponseMessage<>("not-success", 403, "Teacher doensot exist", null, null);
            }


            batch.setSchoolOfTheNepal(school);
            // set the current date while saving the batch
            batch.setCreatedAt();
            batchService.createNewBatch(batch);

            return new ResponseMessage<>("success", 200, "New Batch is created.", null, null);

        } catch (Exception error) {
            return new ResponseMessage<>("error", 500, error.getMessage(), null, null);
        }
    }


    @Setter
    @Getter
    @NoArgsConstructor
    static class schoolidRequestParams {
        int schoolId;

    }
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/get-all-batches")
    public ResponseMessage<List<BatchesModel>> getAllBatches(@RequestBody schoolidRequestParams schoolidRequestParams) {
        try {
            System.out.println(schoolidRequestParams.schoolId + "************ THIS IS THE SCHOOL ID ******");
        List<BatchesModel> batches = batchService.getBatchesBySchoolId(schoolidRequestParams.schoolId);
        if (batches == null || batches.isEmpty()) {
        return new ResponseMessage<>("no-data", 200, "No batches are found related to the school.", null, null);
                        
        }



        System.out.println(batches.get(0).getBatchName() + "THIS IS THE NAME");
        return new ResponseMessage<>("success", 200, "Yoy got the batches", batches, null);

    } catch (Exception error) {
        return new ResponseMessage<>("error", 200, error.getMessage(), null, null);

    }
        
    }



}