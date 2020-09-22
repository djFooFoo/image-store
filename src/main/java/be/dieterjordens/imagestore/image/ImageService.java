package be.dieterjordens.imagestore.image;

public interface ImageService {
    String saveImage(Image image);

    String getImage(String id);
}
