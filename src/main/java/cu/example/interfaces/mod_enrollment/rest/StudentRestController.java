package cu.example.interfaces.mod_enrollment.rest;

import cu.example.application.mod_enrollment.service.StudentService;
import cu.example.application.shared.PersistingTrace;
import cu.example.domain.trace.entity.TraceEvent;
import cu.example.domain.trace.vo.OperationType;
import cu.example.domain.trace.vo.TraceType;
import cu.example.interfaces.mod_enrollment.assembler.StudentModelAssembler;
import cu.example.interfaces.mod_enrollment.model.StudentRequest;
import cu.example.interfaces.mod_enrollment.model.StudentResponse;
import cu.example.interfaces.shared.SuccessfulContentHandler;
import cu.example.interfaces.shared.URIConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = URIConstant.ENTITY_API + URIConstant.API_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentRestController {

    private static final String ENTITY_URI = URIConstant.URI_ENROLL;
    private static final String ENTITY_NAME = URIConstant.ENTITY_NAME_ENROLL;

    private final StudentModelAssembler studentAssembler;
    private final SuccessfulContentHandler contentHandler;

    @Resource
    private PagedResourcesAssembler<StudentResponse> pagedResourcesAssembler;

    @Resource
    private PersistingTrace persisting;

    @Resource
    private StudentService studentService;

    @GetMapping(ENTITY_URI)
    public ResponseEntity<CollectionModel<EntityModel<StudentResponse>>> getAllStudents() {
        List<StudentResponse> students = studentService.getAllStudents();

        persisting.persist(new TraceEvent(TraceType.FUNCIONAL, OperationType.GENERIC_LIST, contentHandler.createSuccessListAlert(ENTITY_NAME), true));

        return ResponseEntity.ok(new CollectionModel<>(studentAssembler.toCollectionModel(students),
                linkTo(methodOn(StudentRestController.class).getAllStudents()).withSelfRel()));
    }

    //20 students per page
    //used header: http://127.0.0.1:8083/univ/v1/students/?page=1&size=20&sort=ci,desc
    //used annotation: http://127.0.0.1:8083/univ/v1/students/
    @GetMapping(path = ENTITY_URI + "/")
    public ResponseEntity<PagedModel<EntityModel<StudentResponse>>> getAllStudentsPageable(@PageableDefault(page = 1, size = 20)
                                                                                           @SortDefault.SortDefaults({
                                                                                                   @SortDefault(sort = "name", direction = Sort.Direction.ASC)
                                                                                           }) Pageable pageable) {

        Page<StudentResponse> students = studentService.getAllStudents(pageable);

        PagedModel<EntityModel<StudentResponse>> collModel = pagedResourcesAssembler
                .toModel(students, studentAssembler);

        persisting.persist(new TraceEvent(TraceType.FUNCIONAL, OperationType.GENERIC_LIST, contentHandler.createSuccessListAlert(ENTITY_NAME), true));

        return ResponseEntity.ok(collModel);
    }

    @GetMapping(ENTITY_URI + "/{studentId}")
    public EntityModel<StudentResponse> getStudentById(@PathVariable long studentId) {
        StudentResponse student = studentService.getStudentById(studentId);
        return studentAssembler.toModel(student);
    }

    @PostMapping(path = ENTITY_URI, consumes = "application/json")
    public ResponseEntity<EntityModel<StudentResponse>> newStudent(@RequestBody StudentRequest studentRequest) {

        StudentResponse newStudent = studentService.addStudent(studentRequest);
        EntityModel<StudentResponse> student = studentAssembler.toModel(newStudent);

        String msg = contentHandler.createEntityCreationAlertMessage(ENTITY_NAME + " " + student.getContent().getName());
        persisting.persist(new TraceEvent(TraceType.FUNCIONAL, OperationType.GENERIC_REGISTER, msg, true));

        return ResponseEntity
                .created(student.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .headers(SuccessfulContentHandler.createHeaders(msg))
                .body(student);
    }

    @PutMapping(path = ENTITY_URI + "/{studentId}", consumes = "application/json")
    public ResponseEntity<EntityModel<StudentResponse>> updateStudent(@PathVariable long studentId, @RequestBody StudentRequest studentRequest) {

        StudentResponse updateStudent = studentService.updateStudent(studentId, studentRequest);

        EntityModel<StudentResponse> student = studentAssembler.toModel(updateStudent);

        String msg = contentHandler.createEntityUpdateAlertMessage(ENTITY_NAME + " " + updateStudent.getName());
        persisting.persist(new TraceEvent(TraceType.FUNCIONAL, OperationType.GENERIC_MODIFY, msg, true));

        return ResponseEntity
                .created(student.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .headers(SuccessfulContentHandler.createHeaders(msg))
                .body(student);
    }

    @DeleteMapping(path = ENTITY_URI + "/{studentId}")
    public ResponseEntity<?> deleteStudentById(@PathVariable int studentId) {
        studentService.deleteStudentById(studentId);

        String msg = contentHandler.createEntityDeleteAlertMessage(ENTITY_NAME + " " + studentId);
        persisting.persist(new TraceEvent(TraceType.FUNCIONAL, OperationType.GENERIC_DELETE, msg, true));

        return ResponseEntity.noContent()
                .headers(SuccessfulContentHandler.createHeaders(msg)).build();
    }
}
