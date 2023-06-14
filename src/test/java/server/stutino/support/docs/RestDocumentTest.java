package server.stutino.support.docs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import server.stutino.auth.filter.JwtAuthenticationFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/*
* 1. @ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
* -> JUnit5 :: Rest Docs + Spring 확장 기능 활성화
*
* 2. @Import(RestDocsConfiguration.class)
* -> RestDocsConfiguration 구성 가져오기
*
* 3. @AutoConfigureRestDocs
* -> 자동으로 RestDocs를 구성하도록 지정
*
* 4. @WebMvcTest
* -> Spring MVC 기반의 웹 어플리케이션 테스트 사용
* */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Import(RestDocsConfiguration.class)
@AutoConfigureRestDocs
@WebMvcTest
public class RestDocumentTest {
    @Autowired
    private ObjectMapper objectMapper;
    /*
    * MockMvc
    * -> 웹 어플리케이션의 웹 계층을 테스트 하기 위해 실제 HTTP 서버를 구동하지 않아도 되므로 테스트 속도를 향상시키고
    * 의존성을 줄일 수 있다. (테스트 중 외부 리소스에 독립적으로 테스트 수행가능)
    * */
    protected MockMvc mockMvc;
    @MockBean private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    protected String toRequestBody(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

    /*
    * 1. @BeforeEach
    * -> 각 테스트 메서드가 실행되기전에 수행되는 초기화 메서드
    * */
    @BeforeEach
    public void setupMockMvc(
            WebApplicationContext ctx,
            RestDocumentationContextProvider restDocumentationContextProvider) {
        mockMvc =
                MockMvcBuilders.webAppContextSetup(ctx)
                        .apply(
                                documentationConfiguration(restDocumentationContextProvider)
                                        .uris()
                                        .withScheme("http")
                                        .withHost("localhost")
                                        .withPort(8080))
//                        .apply(springSecurity(new MockSecurityFilter()))
//                        .addFilter(new CharacterEncodingFilter("UTF-8", true))
                        .alwaysDo(print())
                        .build();
    }
}
