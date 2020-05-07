package cu.example.application.mod_enrollment.service;

import cu.example.application.exception.NotValidIDException;
import cu.example.application.exception.ResourceNotFoundException;
import cu.example.application.mod_enrollment.adapter.TutorAdapter;
import cu.example.application.shared.ValidationUtil;
import cu.example.domain.mod_enrollment.repository.TutorRepository;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import cu.example.interfaces.mod_enrollment.model.TutorRequest;
import cu.example.interfaces.mod_enrollment.model.TutorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Setter
@Getter
@AllArgsConstructor
public class TutorService {

    private final TutorAdapter adapter;
    private final ValidationUtil validation;

    @Resource
    private TutorRepository tutorRepository;

    public TutorResponse getTutorById(long id) {
        if (!validation.isValidId(id))
            throw new NotValidIDException("Student", "id", id);
        else {
            TutorTable tutor = tutorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tutor", "id", id));
            return adapter.mapTo(tutor);
        }
    }
}
