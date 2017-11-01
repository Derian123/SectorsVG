import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * Created by tajhmcdonald on 5/20/17.
 *
 * Image loader class
 *
 */
public class BufferedImageLoader {

    private BufferedImage image;

    public BufferedImage loadImage(String path){
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }



}
