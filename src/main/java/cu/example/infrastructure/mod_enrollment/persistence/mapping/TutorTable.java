package cu.example.infrastructure.mod_enrollment.persistence.mapping;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gloria R. Leyva Jerez
 */
@Entity
@Table(name = "tutor")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TutorTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tutor_id_seq")
    private long id;

    @Column(name = "first_name", length = 10, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 10, nullable = false)
    private String lastName;

    private Boolean doctor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tutor")
    private Set<StudentTable> studentSet = new HashSet<StudentTable>(0);
}
