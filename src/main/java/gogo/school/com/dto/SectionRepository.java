package gogo.school.com.dto;

import org.springframework.data.jpa.repository.JpaRepository;

import gogo.school.com.models.SectionModel;

public interface SectionRepository extends JpaRepository<SectionModel , Integer> {
    
    
}
