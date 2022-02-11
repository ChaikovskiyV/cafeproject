package by.vchaikovski.coffeehouse.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;


/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Picture encoder.
 */
public class PictureEncoder {
    private static final String ENCODE_PARAM = "data:image/jpeg;base64,";
    private static PictureEncoder instance;

    private PictureEncoder() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PictureEncoder getInstance() {
        if (instance == null) {
            instance = new PictureEncoder();
        }
        return instance;
    }

    /**
     * Encode picture string.
     *
     * @param picture the picture
     * @return the string
     */
    public String encodePicture(byte[] picture) {
        StringBuilder sB = new StringBuilder(ENCODE_PARAM);
        byte[] encodeBytes = Base64.encodeBase64(picture, false);
        String str = StringUtils.newStringUtf8(encodeBytes);
        sB.append(str);
        return sB.toString();
    }
}