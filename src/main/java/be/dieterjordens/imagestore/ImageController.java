package be.dieterjordens.imagestore;

import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ImageController {

    public String createImage() {
        String uuid = UUID.randomUUID().toString();

        return uuid;
    }
}
