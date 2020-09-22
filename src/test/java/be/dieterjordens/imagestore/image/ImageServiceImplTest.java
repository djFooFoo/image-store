package be.dieterjordens.imagestore.image;

import be.dieterjordens.imagestore.image.Image;
import be.dieterjordens.imagestore.image.ImageNotFoundException;
import be.dieterjordens.imagestore.image.ImageRepository;
import be.dieterjordens.imagestore.image.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTest {
    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    public void givenValidIdWhenGetImageThenRespondWithBase64String() {
        String validId = "a valid id";
        String base64String = "base64String";
        when(imageRepository.findById(validId)).thenReturn(Optional.of(new Image(base64String)));

        String actualBase64String = imageService.getImage(validId);

        assertThat(actualBase64String).isEqualTo(base64String);
    }

    @Test
    public void givenInvalidIdWhenGetImageThenThrowImageNotFoundException() {
        String inValidId = "a valid id";

        when(imageRepository.findById(inValidId)).thenReturn(Optional.empty());

        assertThrows(ImageNotFoundException.class, () -> imageService.getImage(inValidId));
    }

    @Test
    public void givenImageWhenSaveImageThenRespondWithImageId() {
        String base64String = "base64String";
        Image image = new Image(base64String);
        Image updatedImage = new Image(base64String);
        when(imageRepository.save(image)).thenReturn(updatedImage);

        String id = imageService.saveImage(image);

        assertThat(id).isEqualTo(updatedImage.getId());
    }
}
