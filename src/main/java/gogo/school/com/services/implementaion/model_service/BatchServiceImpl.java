package gogo.school.com.services.implementaion.model_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gogo.school.com.dto.SchoolBatchRepository;
import gogo.school.com.models.BatchesModel;
import gogo.school.com.services.BatchService;

@Service
public class BatchServiceImpl implements BatchService {
    @Autowired
    private SchoolBatchRepository schoolBatchRepository;



    @Override
    public BatchesModel createNewBatch(BatchesModel batch) {
        BatchesModel batchesModel = findBatchBySchoolIdAndBatchName(batch.getSchoolOfTheNepal().getId(),
                batch.getBatchName());
        if (batchesModel != null) {
            System.out.println("haha i run");
            throw new RuntimeException("I think you are creating the same batch twice.");
        }

        return schoolBatchRepository.save(batch);

    }

    @Override
    public BatchesModel findBatchBySchoolIdAndBatchName(int schoolId, String batchName) {
        return schoolBatchRepository.findBySchoolOfTheNepalIdAndBatchName(schoolId, batchName);
    }

    @Override
    public List<BatchesModel> getBatchesBySchoolId(int schoolId) {
        return schoolBatchRepository.findAllBySchoolOfTheNepalId(schoolId);
    } 

}
