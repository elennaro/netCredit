package art.alex.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * Uploads an image
 */
public interface ImageUploadService {
    /**
     * Check file is valid image
     * @param image Image File
     * @return true if valid
     */
    boolean isValidImage(MultipartFile image);

    /**
     * Upload file
     * @param filename Name of the file
     * @param image Image File
     * @return true if file saved
     */
    boolean saveImage(String filename, MultipartFile image);
}
