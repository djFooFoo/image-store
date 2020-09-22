package be.dieterjordens.imagestore.image;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String uuid) {
        super(String.format("Image uuid has not been found: %s", uuid));
    }
}
