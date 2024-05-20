package gogo.school.com.services.implementaion.model_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gogo.school.com.dto.ClassRepository;
import gogo.school.com.models.ClassModel;
import gogo.school.com.services.ClassService;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Override
    public ClassModel createClass(ClassModel classModel) {
        try {
            return this.classRepository.save(classModel);
        } catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }

}
