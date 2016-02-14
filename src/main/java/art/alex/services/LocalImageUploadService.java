package art.alex.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Stores images in local servlet context
 */
@Service
public class LocalImageUploadService implements ImageUploadService, ServletContextAware {

    private static final Logger logger = LoggerFactory.getLogger(LocalImageUploadService.class);

    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * Checks if file is png, jpeg or gif
     *
     * @param image Image File
     * @return true if it is
     */
    @Override public boolean isValidImage(MultipartFile image) {
        return image.getContentType().equals("image/png") ||
                image.getContentType().equals("image/gif") ||
                image.getContentType().equals("image/jpeg");
    }

    /**
     * Uploads image locally to subfolder /images of servlet context
     *
     * @param filename Name of the file
     * @param image    Image File
     * @return true if file saved
     */
    @Override public boolean saveImage(String filename, MultipartFile image) {
        File file = new File(servletContext.getRealPath("/") + "/images/" + filename);
        //noinspection ResultOfMethodCallIgnored
        file.getParentFile().mkdirs();

        try (BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(file))) {
            byte[] bytes = image.getBytes();
            buffStream.write(bytes);
            buffStream.close();
            return true;
        } catch (IOException ex) {
            logger.warn("Could not save image due to IOException", ex);
            return false;
        }
    }
}
