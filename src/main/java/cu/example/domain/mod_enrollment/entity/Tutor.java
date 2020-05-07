package cu.example.domain.mod_enrollment.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gloria R. Leyva Jerez
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tutor implements Serializable {

    private static final long serialVersionUID = 9153587198765144792L;

    @NotBlank(message = "{notBlank.id}")
    private long id;

    @NotBlank(message = "{notBlank.firstName}")
    @Size(max = 10, message = "{size.firstName}")
    private String firstName;

    @Column(name = "last_name", length = 10, nullable = false)
    private String lastName;

    private Boolean doctor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tutor")
    private Set<Student> studentSet = new HashSet<>(0);
}
