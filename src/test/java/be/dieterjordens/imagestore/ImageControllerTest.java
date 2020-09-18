package be.dieterjordens.imagestore;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ImageControllerTest {

    @InjectMocks
    private ImageController imageController;

    @BeforeEach
    public void setupRestAssured() {
        RestAssuredMockMvc.standaloneSetup(imageController);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void givenNoExistingCoursesWhenGetCoursesThenRespondWithStatusOkAndEmptyArray() {

    }
}
