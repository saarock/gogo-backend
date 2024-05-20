package gogo.school.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gogo.school.com.models.SectionModel;
import gogo.school.com.services.SectionService;
import gogo.school.com.utils.ResponseMessage;

@RestController
public class SectionController {
    @Autowired
    private SectionService sectionService;

    @PostMapping("/create-section")
    public ResponseMessage<SectionModel> createNewSection(@RequestBody SectionModel sectionModel) {
        try {
            
            SectionModel newSection = this.sectionService.createNewSection(sectionModel);

            if (newSection == null) {
                return new ResponseMessage<>("not-success", 5000, "Something wrong while createing the newSection",
                        null, null);
            }
            return new ResponseMessage<>("success", 5000, "SSection createdSuccessfully", newSection, null);

        } catch (Exception error) {
            error.printStackTrace();
            return new ResponseMessage<>("error", 5000, error.getMessage(), null, null);

        }
    }
}
