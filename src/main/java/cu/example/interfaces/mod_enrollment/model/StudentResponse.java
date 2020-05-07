package cu.example.interfaces.mod_enrollment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"id"})
public class StudentResponse implements Serializable {

    private static final long serialVersionUID = 3077294558162556832L;

    private long id;

    private String ci;

    private String name;

    @JsonProperty("firs_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    private Boolean active;

    @JsonIgnore
    private long tutor;

}
