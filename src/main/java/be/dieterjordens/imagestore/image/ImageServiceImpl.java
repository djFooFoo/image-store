package be.dieterjordens.imagestore.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public String saveImage(Image image){
        Image updatedImage = imageRepository.save(image);
        return updatedImage.getId();
    }

    @Override
    public String getImage(String id) {
        return imageRepository.findById(id)
                .map(Image::getBase64String)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }
}
