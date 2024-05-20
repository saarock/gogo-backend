package gogo.school.com.dto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import gogo.school.com.models.TeacherModel;
import java.util.List;


public interface teacherRespository extends JpaRepository<TeacherModel, Integer> {
    Optional<TeacherModel> findByTeacherMail(String teacherMail);
}
