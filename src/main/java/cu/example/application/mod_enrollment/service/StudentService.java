package cu.example.application.mod_enrollment.service;

import cu.example.application.exception.NotValidIDException;
import cu.example.application.exception.ResourceNotFoundException;
import cu.example.application.mod_enrollment.adapter.StudentAdapter;
import cu.example.application.shared.ValidationUtil;
import cu.example.domain.mod_enrollment.entity.Student;
import cu.example.domain.mod_enrollment.repository.StudentRepository;
import cu.example.domain.mod_enrollment.service.StudentFactory;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;
import cu.example.interfaces.mod_enrollment.model.StudentRequest;
import cu.example.interfaces.mod_enrollment.model.StudentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Setter
@Getter
@AllArgsConstructor
@Validated
public class StudentService {

    private final StudentAdapter adapter;
    private final ValidationUtil validation;

    @Resource
    private StudentFactory factory;

    @Resource
    private StudentRepository repository;

    public List<StudentResponse> getAllStudents() {

        return repository.findAll().stream()
                .map(adapter::mapTo)
                .collect(Collectors.toList());
    }

    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        List<StudentResponse> all = repository.findAll(pageable).stream()
                .map(adapter::mapTo)
                .collect(Collectors.toList());

        return new PageImpl(all, pageable, all.size());
    }

    public StudentResponse getStudentById(long id) {
        if (!validation.isValidId(id))
            throw new NotValidIDException("Student", "id", id);
        else {
            StudentTable student = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
            return adapter.mapTo(student);
        }
    }

    public StudentResponse addStudent(StudentRequest studentRequest) {
        Student studentToAdd = adapter.mapTo(studentRequest);
        StudentTable student = factory.createFrom(studentToAdd, studentRequest.getTutor());
        return adapter.mapTo(repository.save(student));
    }

    public StudentResponse updateStudent(long studentId, StudentRequest studentRepresentation) {
        if (!validation.isValidId(studentId))
            throw new NotValidIDException("Student", "id", studentId);
        else {
            StudentTable studentTable = repository.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

            Student studentToUpdate = adapter.mapTo(studentRepresentation);
            StudentTable student = factory.createFrom(studentToUpdate, studentRepresentation.getTutor());
            student.setId(studentId);
            return adapter.mapTo(repository.save(student));

        }
    }

    public void deleteStudentById(long id) {
        if (!validation.isValidId(id))
            throw new NotValidIDException("Student", "id", id);
        else {
            StudentTable student = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Student", "ci", id));
            repository.delete(student);
        }
    }
}
