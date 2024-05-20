package gogo.school.com.services.implementaion.model_service;

import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gogo.school.com.dto.SchoolRepository;
import gogo.school.com.models.BatchesModel;
import gogo.school.com.models.SchoolModel;
import gogo.school.com.services.SchoolService;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public boolean saveNewSchool(SchoolModel school) {
     SchoolModel schoolSavedOrNot = schoolRepository.save(school);
     return schoolSavedOrNot != null;
    }

    @Override
    public Set<BatchesModel> getBatchesBySchoolID(int schoolId) {
        Optional<SchoolModel> optionalSchool = schoolRepository.findById(schoolId);
        if (optionalSchool.isPresent()) {
            SchoolModel school = optionalSchool.get();
            return school.getBatches();
        }
        return null;
    }

    @Override
    public SchoolModel findById(int id) {
        return schoolRepository.findById(id).orElse(null);
    }

    @Override
    public boolean checkEmailExistOrNot(String email) {
        SchoolModel schoolModelToRelatedToTheCurrentEmail = schoolRepository.findBySchoolEmail(email);
        if (schoolModelToRelatedToTheCurrentEmail != null) {
            return false;
        }
        return false;
    }

}
