package gogo.school.com.dto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gogo.school.com.models.BatchesModel;

public interface SchoolBatchRepository extends JpaRepository<BatchesModel, Integer> {
    /**
     * Throwin the error becauser datatype is not matching in the school id;
     */
    BatchesModel findBySchoolOfTheNepalIdAndBatchName(int schoolId, String batchName);

    List<BatchesModel> findAllBySchoolOfTheNepalId(int schoolId);
}
