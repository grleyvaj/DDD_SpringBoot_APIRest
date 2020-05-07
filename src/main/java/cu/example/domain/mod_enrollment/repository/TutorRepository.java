package cu.example.domain.mod_enrollment.repository;

import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;

import java.util.List;
import java.util.Optional;

public interface TutorRepository {

    public List<TutorTable> findAll();

    public Optional<TutorTable> findById(long id);

    public TutorTable save(TutorTable tutor);

    public void delete(TutorTable tutor);
}
