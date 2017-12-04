package resources;

import gui.ErrorMessage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Pierre on 2016-12-05.
 */
public class ImageLoader {
    private ErrorMessage em = new ErrorMessage();
    private BufferedImage image;

    public BufferedImage loadImage(String path){
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            em.display(path, e.getLocalizedMessage());
        } catch (IllegalArgumentException e){
            em.display(path, e.getLocalizedMessage());
        }
        return image;
    }
}
