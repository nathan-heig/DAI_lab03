package ch.bdr.ImdbLike.Utils;

import java.util.Base64;

public class ImageUtil {

    public static String encodeToBase64(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }
}