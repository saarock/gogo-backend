package gogo.school.com.services;

import java.util.Optional;

import gogo.school.com.models.TeacherModel;

public interface TeacherService {
    public TeacherModel createNewTeacher(TeacherModel teacherModel);
    public boolean verifyTeacher(String teacherId);
    public boolean isEmpty();
    public Optional<TeacherModel> findTeacherByEmail(String teacherMail);
    public TeacherModel findById(int id);

}
