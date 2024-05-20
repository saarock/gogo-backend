package gogo.school.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import gogo.school.com.models.ClassModel;
import gogo.school.com.services.ClassService;
import gogo.school.com.utils.ResponseMessage;

@RestController
public class ClassController {
    @Autowired
    private ClassService classService;

    @PostMapping("/create-class")
    public ResponseMessage<ClassModel> createClass(@RequestBody ClassModel classModel) {
        ClassModel newClass = this.classService.createClass(classModel);
        if (newClass == null) {
            return new ResponseMessage<>("not-success", 500, "SOneThing wrong while creating the class", null, null);
        }

        return new ResponseMessage<>("success", 200, "new class is created", newClass, null);

    }
    
}
