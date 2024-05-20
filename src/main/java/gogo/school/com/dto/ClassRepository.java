package gogo.school.com.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import gogo.school.com.models.ClassModel;

public interface ClassRepository extends JpaRepository<ClassModel, Integer> {
    
}
