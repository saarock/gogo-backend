package gogo.school.com.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import gogo.school.com.models.SchoolModel;

public interface SchoolRepository extends JpaRepository<SchoolModel, Integer> {
    /**
     * Finds a school by its email address.
     * 
     * @param schoolEmail The email address of the school to find.
     * @return The SchoolModel corresponding to the given email, or null if not
     *         found.
     */
    SchoolModel findBySchoolEmail(String schoolEmail);
}
