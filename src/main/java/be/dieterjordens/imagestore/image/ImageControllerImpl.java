package be.dieterjordens.imagestore.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = ImageController.PATH)
public class ImageControllerImpl implements ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageControllerImpl(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    @PostMapping
    public String createImage(@RequestBody Image image) {
        return imageService.saveImage(image);
    }

    @Override
    @GetMapping(value = "/{id}")
    public String getImage(@PathVariable String id) {
        return imageService.getImage(id);
    }
}
