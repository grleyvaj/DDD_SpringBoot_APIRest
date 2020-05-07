package cu.example.infrastructure.mod_enrollment.persistence.repository;

import cu.example.domain.mod_enrollment.repository.TutorRepository;
import cu.example.infrastructure.mod_enrollment.imports.ITutorJPARepository;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class TutorRepositoryImpl implements TutorRepository {

    private final ITutorJPARepository impl;

    @Override
    public List<TutorTable> findAll() {
        return impl.findAll();
    }

    @Override
    public Optional<TutorTable> findById(long id) {
        return impl.findById(id);
    }

    @Override
    public TutorTable save(TutorTable tutor) {
        return impl.save(tutor);
    }

    @Override
    public void delete(TutorTable tutor) {
        impl.delete(tutor);
    }
}
