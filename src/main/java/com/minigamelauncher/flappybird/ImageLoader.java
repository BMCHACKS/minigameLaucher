package com.minigamelauncher.flappybird;

import javafx.scene.image.Image;
import java.io.InputStream;

public class ImageLoader {

    private ImageLoader() {
    }

    public static Image load(String filename) {
        try {
            InputStream stream = ImageLoader.class.getResourceAsStream(filename);
            if (stream == null) {
                throw new ResourceLoadException(filename);
            }
            return new Image(stream);
        } catch (ResourceLoadException e) {
            throw e;
        } catch (Exception e) {
            throw new ResourceLoadException(filename, e);
        }
    }
}
