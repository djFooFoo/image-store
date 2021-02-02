package be.dieterjordens.imagestore.image;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

public interface ImageController {
    String PATH = "/images/";

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    String createImage(@RequestBody Image image);

    @GetMapping(value = "{identifier}", produces = MediaType.IMAGE_JPEG_VALUE)
    byte[] getImage(@PathVariable String identifier);
}
