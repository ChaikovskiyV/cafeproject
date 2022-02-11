package by.vchaikovski.coffeehouse.util;

import by.vchaikovski.coffeehouse.model.entity.Menu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ResourceBundle;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Picture loader.
 */
public class PictureLoader {
    private static final Logger logger = LogManager.getLogger();
    private static final String PICTURE_PATH_PROP = "picturepath";
    private static final String DEFAULT_IMAGE_COFFEE = "defaultImage.coffee";
    private static final String DEFAULT_IMAGE_TEA = "defaultImage.tea";
    private static final String DEFAULT_IMAGE_PASTRY = "defaultImage.pastry";
    private static final String ABSOLUTE = "image.absolute";
    private static PictureLoader instance;
    private final ResourceBundle pictureBundle;

    private PictureLoader() {
        pictureBundle = ResourceBundle.getBundle(PICTURE_PATH_PROP);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PictureLoader getInstance() {
        if (instance == null) {
            instance = new PictureLoader();
        }
        return instance;
    }

    /**
     * Load default picture byte [ ].
     *
     * @param type the type
     * @return the byte [ ]
     */
    public byte[] loadDefaultPicture(Menu.FoodType type) {
        byte[] imageBytes = null;
        Path imagePath = switch (type) {
            case COFFEE -> Path.of(pictureBundle.getString(DEFAULT_IMAGE_COFFEE));
            case TEA -> Path.of(pictureBundle.getString(DEFAULT_IMAGE_TEA));
            case PASTRY -> Path.of(pictureBundle.getString(DEFAULT_IMAGE_PASTRY));
        };
        try (FileInputStream inputStream = new FileInputStream(imagePath.toFile())) {
            imageBytes = inputStream.readAllBytes();
        } catch (IOException e) {
            logger.error(() -> "The file " + imagePath + " was not found", e);
        }
        return imageBytes;
    }

    /**
     * Load picture byte [ ].
     *
     * @param filename the filename
     * @return the byte [ ]
     */
    public byte[] loadPicture(String filename) {
        Path imagePath = Path.of(pictureBundle.getString(ABSOLUTE) + filename).toAbsolutePath();
        byte[] imageBytes = null;
        try (FileInputStream inputStream = new FileInputStream(imagePath.toFile())) {
            imageBytes = inputStream.readAllBytes();
        } catch (IOException e) {
            logger.error(() -> "The file " + filename + " was not found", e);
        }
        return imageBytes;
    }
}