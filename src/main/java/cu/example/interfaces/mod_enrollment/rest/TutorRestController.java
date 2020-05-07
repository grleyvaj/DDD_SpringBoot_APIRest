package cu.example.interfaces.mod_enrollment.rest;

import cu.example.application.mod_enrollment.service.StudentService;
import cu.example.application.mod_enrollment.service.TutorService;
import cu.example.interfaces.mod_enrollment.assembler.TutorModelAssembler;
import cu.example.interfaces.mod_enrollment.model.TutorResponse;
import cu.example.interfaces.shared.URIConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = URIConstant.ENTITY_API + URIConstant.API_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class TutorRestController {

    private static final String ENTITY_URI = URIConstant.URI_TUTOR;

    private final TutorModelAssembler assembler;

    @Resource
    private TutorService tutorService;

    @Resource
    private StudentService studentService;

    @GetMapping("/students" + "/{studentId}" + ENTITY_URI)
    public EntityModel<TutorResponse> getTutorFromStudent(@PathVariable long studentId) {
        long tutorId = studentService.getStudentById(studentId).getTutor();
        TutorResponse tutor = tutorService.getTutorById(tutorId);
        return assembler.toModel(tutor);
    }
}
