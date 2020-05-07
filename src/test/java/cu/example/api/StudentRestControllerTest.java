package cu.example.api;

import cu.example.application.mod_enrollment.service.StudentService;
import cu.example.interfaces.mod_enrollment.model.StudentRequest;
import cu.example.interfaces.mod_enrollment.model.StudentResponse;
import cu.example.interfaces.shared.URIConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class StudentRestControllerTest {

    @Resource
    StudentService studentService;

    private MockMvc mockMvc;

    private static final String URI = URIConstant.ENTITY_API + URIConstant.API_VERSION + URIConstant.URI_ENROLL;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .snippets().withEncoding("ISO-8859-1"))
//                .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    public void getAllStudents() throws Exception {
        this.mockMvc.perform(get(URI).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("student/get-student-all"));
    }

    @Test
    public void getStudentById() throws Exception {

        //Create student to delete
        StudentRequest studentRequest = StudentRequest.builder()
                .ci("12375678911")
                .name("Gloria")
                .firstName("Gloria")
                .lastName("Leyva")
                .email("correo@gmail.com")
                .active(true)
                .tutor(1)
                .build();

        StudentResponse save = studentService.addStudent(studentRequest);
        long id = save.getId();

        this.mockMvc.perform(get(URI + "/{studentId}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("student/get-student-by-id"));

        studentService.deleteStudentById(id);
    }

    @Test
    public void deleteStudentById() throws Exception {

        //Create student to delete
        StudentRequest studentRequest = StudentRequest.builder()
                .ci("12375678911")
                .name("Gloria")
                .firstName("Gloria")
                .lastName("Leyva")
                .email("correo@gmail.com")
                .active(true)
                .tutor(1)
                .build();

        StudentResponse save = studentService.addStudent(studentRequest);
        long id = save.getId();

        this.mockMvc.perform(delete(URI + "/{studentId}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("student/delete-student-by-id", pathParameters(parameterWithName("studentId").description("The student id of the input to delete"))));
    }

    @Test
    public void saveStudent() throws Exception {
    }

}
