package cu.example.application.mod_enrollment.adapter;

import com.sun.jdi.LongValue;
import cu.example.interfaces.mod_enrollment.model.StudentRequest;
import cu.example.domain.mod_enrollment.entity.Student;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;

import cu.example.interfaces.mod_enrollment.model.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class StudentAdapter {

    public Student mapTo(StudentRequest studentRepresentation) {
        Student build = Student.builder()
                .ci(studentRepresentation.getCi())
                .fullName(studentRepresentation.getName())
                .firstName(studentRepresentation.getFirstName())
                .lastName(studentRepresentation.getLastName())
                .email(studentRepresentation.getEmail().isEmpty() ? null : studentRepresentation.getEmail())
                .active(true) //default create is true
                .build();
        return build;
    }

    public StudentResponse mapTo(StudentTable student){
        return StudentResponse.builder()
                .id(student.getId())
                .ci(student.getCi())
                .name(student.getName())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .active(student.getActive())
                .tutor(student.getTutor().getId()).build();
    }

}
