
package gogo.school.com.services.implementaion.model_service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gogo.school.com.dto.SectionRepository;
import gogo.school.com.models.SectionModel;
import gogo.school.com.services.SectionService;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Override
    public SectionModel createNewSection(SectionModel sectionModel) {
        try {
            Optional<SectionModel> newSectionOptional = Optional.ofNullable(this.sectionRepository.save(sectionModel));
            return newSectionOptional.orElse(null);
        } catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }

}
