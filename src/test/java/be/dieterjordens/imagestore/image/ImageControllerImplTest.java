package be.dieterjordens.imagestore.image;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageControllerImplTest {
    @Mock
    private ImageService imageService;

    @InjectMocks
    private ImageControllerImpl imageController;

    @Test
    public void givenAnExistingImageWhenGetImageThenRespondWithImage() {
        String existingId = "bla";
        byte[] imageBytes = "existingImage".getBytes();
        String base64image = Base64.getMimeEncoder().encodeToString(imageBytes);

        when(imageService.getImage(existingId)).thenReturn(base64image);

        assertThat(imageController.getImage(existingId)).isEqualTo(imageBytes);
    }

    @Test
    public void givenImageWhenCreateImageThenRespondWithId() {
        Image image = new Image("base64String");
        String id = "id of saved image";

        when(imageService.saveImage(image)).thenReturn(id);

        assertThat(imageController.createImage(image)).isEqualTo(id);
    }
}
