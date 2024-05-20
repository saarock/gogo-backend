package gogo.school.com.services;

import java.util.Set;
import gogo.school.com.models.BatchesModel;
import gogo.school.com.models.SchoolModel;

public interface SchoolService {

    public boolean saveNewSchool(SchoolModel school);

    public Set<BatchesModel> getBatchesBySchoolID(int schoolId);

    public SchoolModel findById(int id);

    public boolean checkEmailExistOrNot(String email);


}
