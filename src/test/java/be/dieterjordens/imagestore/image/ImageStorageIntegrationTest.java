package be.dieterjordens.imagestore.image;

import be.dieterjordens.imagestore.image.Image;
import be.dieterjordens.imagestore.image.ImageController;
import be.dieterjordens.imagestore.image.ImageRepository;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ImageStorageIntegrationTest {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void givenNoMatchingIdWhenGetImageThenRespondWithStatusNotFound() {
        String nonMatchingId = "nonMatchingImageCode";

        given()
                .when()
                    .get(ImageController.PATH + nonMatchingId)
                .then()
                    .statusCode(NOT_FOUND.value());
    }

    @Test
    public void givenExistingIdWhenGetImageThenRespondWithImage() {
        String base64StringImage = "a base 64 image";
        Image savedImage = imageRepository.save(new Image(base64StringImage));

        String base64String = given()
                    .when()
                .get(ImageController.PATH + savedImage.getId())
                    .then()
                    .statusCode(OK.value())
                    .extract().body().asString();

        assertThat(base64String).isEqualTo(savedImage.getBase64String());
    }

    @Test
    public void givenImageWhenCreateImageThenRespondWithId() throws IOException {
        String base64String = getBase64ImageFromFile();
        System.out.println(base64String);

        String jsonBody = String.format("""
                   {
                        "base64String" : "%s"
                   }
                """, base64String);

        String id = given()
                .when()
                    .contentType(ContentType.JSON)
                    .body(jsonBody)
                    .post(ImageController.PATH)
                .then()
                    .statusCode(OK.value())
                    .extract().body().asString();

        Optional<Image> image = imageRepository.findById(id);
        assertThat(image).isPresent();
        assertThat(image.get().getId()).isEqualTo(id);
    }

    private String getBase64ImageFromFile() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("base64image.txt")).getFile());
        return Files.readString(file.toPath()).replace("\n", "");
    }
}
