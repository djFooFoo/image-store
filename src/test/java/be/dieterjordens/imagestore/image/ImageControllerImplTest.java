package be.dieterjordens.imagestore.image;

import be.dieterjordens.imagestore.image.Image;
import be.dieterjordens.imagestore.image.ImageController;
import be.dieterjordens.imagestore.image.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        String existingImage = "existingImage";

        when(imageService.getImage(existingId)).thenReturn(existingImage);

        assertThat(imageController.getImage(existingId)).isEqualTo(existingImage);
    }

    @Test
    public void givenImageWhenCreateImageThenRespondWithId() {
        Image image = new Image("base64String");
        String id = "id of saved image";

        when(imageService.saveImage(image)).thenReturn(id);

        assertThat(imageController.createImage(image)).isEqualTo(id);
    }
}
