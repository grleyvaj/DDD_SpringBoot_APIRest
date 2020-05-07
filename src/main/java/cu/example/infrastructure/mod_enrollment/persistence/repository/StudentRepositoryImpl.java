package cu.example.infrastructure.mod_enrollment.persistence.repository;

import cu.example.domain.mod_enrollment.repository.StudentRepository;
import cu.example.infrastructure.mod_enrollment.imports.IStudentJPARepository;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {

    private final IStudentJPARepository impl;

    @Override
    public List<StudentTable> findAll() {
        return impl.findAll();
    }

    @Override
    public Page<StudentTable> findAll(Pageable pageable) {
        return impl.findAll(pageable);
    }

    @Override
    public Optional<StudentTable> findById(long id) {
        return impl.findById(id);
    }

    @Override
    public StudentTable save(StudentTable student) {
        return impl.save(student);
    }

    @Override
    public void delete(StudentTable student) {
        impl.delete(student);
    }
}
