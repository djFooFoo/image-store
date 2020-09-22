package be.dieterjordens.imagestore.image;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ImageController {
    String PATH = "/images/";

    String createImage(@RequestBody Image image);

    String getImage(@PathVariable String id);
}
