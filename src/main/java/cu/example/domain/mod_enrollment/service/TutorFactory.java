package cu.example.domain.mod_enrollment.service;

import cu.example.domain.mod_enrollment.entity.Tutor;
import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@Data
public class TutorFactory {

    public TutorTable createFrom(Tutor tutor, long tutorId) {

        return TutorTable.builder()
//                .id(student.getId())
                .firstName(tutor.getFirstName())
                .lastName(tutor.getLastName())
                .doctor(tutor.getDoctor())
                .build();
    }

    public Tutor createFrom(TutorTable tutorTable) {

        return Tutor.builder()
                .id(tutorTable.getId())
                .firstName(tutorTable.getFirstName())
                .lastName(tutorTable.getLastName())
                .doctor(tutorTable.getDoctor())
                .build();
    }

}
