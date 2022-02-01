package by.vchaikovski.coffeeshop.util;

import by.vchaikovski.coffeeshop.model.entity.Menu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class PictureLoader {
    private static final Logger logger = LogManager.getLogger();
    private static final String PICTURE_PATH_PROP = "picturepath";
    private static final String DEFAULT_IMAGE_COFFEE = "defaultImage.coffee";
    private static final String DEFAULT_IMAGE_TEA = "defaultImage.tea";
    private static final String DEFAULT_IMAGE_PASTRY = "defaultImage.pastry";
    private static PictureLoader instance;
    private final ResourceBundle pictureBundle;

    private PictureLoader() {
        pictureBundle = ResourceBundle.getBundle(PICTURE_PATH_PROP);
    }

    public static PictureLoader getInstance() {
        if (instance == null) {
            instance = new PictureLoader();
        }
        return instance;
    }

    public byte[] loadDefaultPicture(Menu.FoodType type) {
        byte[] imageBytes = null;
        Path imagePath = switch (type) {
            case COFFEE -> Path.of(pictureBundle.getString(DEFAULT_IMAGE_COFFEE));
            case TEA -> Path.of(pictureBundle.getString(DEFAULT_IMAGE_TEA));
            case PASTRY -> Path.of(pictureBundle.getString(DEFAULT_IMAGE_PASTRY));
        };
        try {
            imageBytes = Files.readAllBytes(imagePath);
        } catch (IOException e) {
            logger.warn(() -> "The file " + imagePath + " was not found", e); //TODO Throw own exception or not
        }
        return imageBytes;
    }

    public byte[] loadPicture(String filename) {
        Path imagePath = Path.of(filename);
        byte[] imageBytes = null;
        try {
            imageBytes = Files.readAllBytes(imagePath);
        } catch (IOException e) {
            logger.warn(() -> "The file " + filename + " was not found", e);//TODO Throw own exception or not
        }
        return imageBytes;
    }
}