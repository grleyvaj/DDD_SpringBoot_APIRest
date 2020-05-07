package cu.example.interfaces.mod_enrollment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRequest implements Serializable {

    private static final long serialVersionUID = 3959425423731292762L;

    private long id;

    private String ci;

    private String name;

    @JsonProperty("firs_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    private Boolean active;

    private long tutor;
}
