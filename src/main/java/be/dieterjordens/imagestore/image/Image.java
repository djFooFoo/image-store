package be.dieterjordens.imagestore.image;

import org.springframework.data.annotation.Id;

public class Image {
    @Id
    private String id;

    private String base64String;

    public Image() {
    }

    public Image(String base64String) {
        this.base64String = base64String;
    }

    public String getBase64String() {
        return base64String;
    }

    public void setBase64String(String base64String) {
        this.base64String = base64String;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
