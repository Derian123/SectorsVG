import java.awt.*;
import java.awt.Graphics;
import java.io.File;

/**
 * Created by tajhmcdonald on 5/20/17.
 */
class Background extends Canvas
{
    Image image;
    public Background()
    {
        try
        {

            image = javax.imageio.ImageIO.read(new File("background/spcae0.jpg"));
        }
        catch (Exception e) { /*handled in paintComponent()*/ }
    }
    protected void paintComponent(Graphics g)
    {
        if (image != null)
            g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),this);
    }
}