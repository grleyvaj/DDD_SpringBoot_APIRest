package cu.example.domain.mod_enrollment.repository;

import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {

    public List<StudentTable> findAll();

    public Page<StudentTable> findAll(Pageable pageable);

    public Optional<StudentTable> findById(long id);

    public StudentTable save(StudentTable student);

    public void delete(StudentTable student);
}
