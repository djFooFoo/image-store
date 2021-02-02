package be.dieterjordens.imagestore.image;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
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
    @LocalServerPort
    private int port;

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
                .port(port)
                .when()
                    .get(ImageController.PATH + nonMatchingId)
                .then()
                    .statusCode(NOT_FOUND.value());
    }

    @Test
    public void givenExistingIdWhenGetImageThenRespondWithImage() {
        byte[] expectedImageBytes = "a base 64 image".getBytes();
        String base64StringImage = Base64.getMimeEncoder().encodeToString(expectedImageBytes);
        Image savedImage = imageRepository.save(new Image(base64StringImage));

        byte[] actualImageBytes = given()
                .port(port)
                    .when()
                .get(ImageController.PATH + savedImage.getId())
                    .then()
                    .statusCode(OK.value())
                    .extract().body().asByteArray();

        assertThat(actualImageBytes).isEqualTo(expectedImageBytes);
    }

    @Test
    public void givenImageWhenCreateImageThenRespondWithId() {
        String given_id = "an id";
        byte[] expectedImageBytes = "a base 64 image".getBytes();
        String base64String = Base64.getMimeEncoder().encodeToString(expectedImageBytes);

        String jsonBody = String.format("""
                   {
                        "id": "%s",
                        "base64String" : "%s"
                   }
                """, given_id, base64String);

        String id = given()
                .port(port)
                .when()
                    .contentType(ContentType.JSON)
                    .body(jsonBody)
                    .put(ImageController.PATH)
                .then()
                    .statusCode(OK.value())
                    .extract().body().asString();

        Optional<Image> image = imageRepository.findById(id);
        assertThat(image).isPresent();
        assertThat(image.get().getId()).isEqualTo(id);
    }
}
