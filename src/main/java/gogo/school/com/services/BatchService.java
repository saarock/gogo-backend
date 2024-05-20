package gogo.school.com.services;

import java.util.List;

import gogo.school.com.models.BatchesModel;
import gogo.school.com.models.TeacherModel;

public interface BatchService {
    /**
     * @param batch
     * @return BatchesModel
     */
    public BatchesModel createNewBatch(BatchesModel batch);

    /**
     * 
     * @param schoolId
     * @param batchName
     * @return BatchesModel
     */
    public BatchesModel findBatchBySchoolIdAndBatchName(int schoolId, String batchName);


    public List<BatchesModel> getBatchesBySchoolId(int schoolId) ;

}
