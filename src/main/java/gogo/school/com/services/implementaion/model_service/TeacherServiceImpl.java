package gogo.school.com.services.implementaion.model_service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gogo.school.com.dto.teacherRespository;
import gogo.school.com.models.TeacherModel;
import gogo.school.com.services.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private teacherRespository teacherRepository;

    public TeacherModel createNewTeacher(TeacherModel teacherModel) {
        try {
            TeacherModel savedTeacher = this.teacherRepository.save(teacherModel);
            System.out.println("Saved to the database");
            TeacherModel responseTeacher = new TeacherModel();
            responseTeacher.setId(savedTeacher.getId());
            responseTeacher.setTeacherName(savedTeacher.getTeacherName());
            responseTeacher.setTeacherAddress(savedTeacher.getTeacherAddress());
            responseTeacher.setTeacherPhoneNumber(savedTeacher.getTeacherPhoneNumber());
            responseTeacher.setTeacherMail(savedTeacher.getTeacherMail());
            responseTeacher.setActive(savedTeacher.isActive());
            responseTeacher.setAdmin(savedTeacher.isAdmin());
            responseTeacher.setSchoolModel(savedTeacher.getSchoolModel());
            return responseTeacher;
        } catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }

    public TeacherModel findById(int id) {
        Optional<TeacherModel> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()) {
            TeacherModel teacherModel = teacher.get();
            return teacherModel;
        }

        return null;

    }

    public boolean verifyTeacher(String teacherId) {

        return true;
    }

    public boolean isEmpty() {
        return teacherRepository.count() == 0;

    }

    public Optional<TeacherModel> findTeacherByEmail(String email) {
        return teacherRepository.findByTeacherMail(email);
    }
}
