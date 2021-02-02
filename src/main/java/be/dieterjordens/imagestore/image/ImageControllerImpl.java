package be.dieterjordens.imagestore.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping(path = ImageController.PATH)
public class ImageControllerImpl implements ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageControllerImpl(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createImage(@RequestBody Image image) {
        return imageService.saveImage(image);
    }

    @Override
    @GetMapping(value = "{identifier}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String identifier) {
        Base64.Decoder decoder = Base64.getMimeDecoder();
        return decoder.decode(imageService.getImage(identifier));
    }
}
