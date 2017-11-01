import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Images {//everything is static to save memory. only one copy is used
	
	private static BufferedImage spaceShip;
	
	private static BufferedImage[] rocks = new BufferedImage[6];
	
	private static BufferedImage[] mapImages = new BufferedImage[4];

	private static BufferedImage background = null;

	public Images(){//creates the images
		
		try {
            background = ImageIO.read(new File("Res/Planet1.png"));

            spaceShip = ImageIO.read(new File("Res/Spaceship.gif"));

			rocks[0] = ImageIO.read(new File("Res/Rock1.gif"));
			rocks[1] = ImageIO.read(new File("Res/Rock2.gif"));
			rocks[2] = ImageIO.read(new File("Res/Rock3.gif"));
			rocks[3] = ImageIO.read(new File("Res/Rock4.gif"));
			rocks[4] = ImageIO.read(new File("Res/Rock5.gif"));
			rocks[5] = ImageIO.read(new File("Res/Rock6.gif"));

			mapImages[0] = ImageIO.read(new File("Res/Spaceship.gif"));
			mapImages[1] = ImageIO.read(new File("Res/Spaceship.gif"));
			mapImages[2] = ImageIO.read(new File("Res/Spaceship.gif"));
			mapImages[3] = ImageIO.read(new File("Res/Spaceship.gif"));
		    
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}



	public static BufferedImage getSpaceShip() {

		return spaceShip;
	}

	public static BufferedImage getRocks(int index) {

		return rocks[index];
	}
	
	public static BufferedImage getMapImages(int index){

		return mapImages[index];
	}

	public static BufferedImage getBackground(){
		return background;}
}
