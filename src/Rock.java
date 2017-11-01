import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;



public class Rock extends GameObject {

	private BufferedImage rockImage;
	private int rockNumber; //used for collision calculations with the rocks because each rock image is a different size
	
	private int locY;
	private int locX;
	private int radius;
	
	private int sector = 0;
	private int sectorColumn;
	
	private boolean isCollided = false;
	private boolean isBelowScreen = false;
	
	private static boolean debugMode = false;//we make this static because if debug mode is on we want all objects to be in debug mode
	private static boolean staticRocks = false;//testing method. makes the rocks not move

	//private int velocity;
	
	
	
	public Rock(int x, int y, ID id) {
		super(x, y, id);
		
		locX = x;
		locY = y; 
		calculateRadius();
		calculateInitialSector();
	}
	
	public int getLocY() {
		return locY;
	}

	public int getLocX() {
		return locX;
	}

	public void setRockImage(BufferedImage rockImage) {
		this.rockImage = rockImage;
	}

	public int getRockNumber() {
		return rockNumber;
	}

	public void setRockNumber(int rockNumber) {
		this.rockNumber = rockNumber;
	}
	
	public int getRadius() {
		return radius;
	}

	private void calculateRadius(){
		if(rockNumber == 0){
			radius = 55;
			return;
		}
		if(rockNumber == 1){
			radius = 52;
			return;
		}
		if(rockNumber == 2){
			radius = 36;
			return;
		}
		if(rockNumber == 3){
			radius = 30;
			return;
		}
		if(rockNumber == 4){
			radius = 27;
			return;
		}
		if(rockNumber == 5){
			radius = 18;
			return;
		}
		
	}

	public boolean isCollided() {
		return isCollided;
	}

	public void setCollided() {
		isCollided = true;
		locX = -100;// we do this because the program will stop drawing the rock but it still may be in its location for a second or two causing collisions when it should
		locY = -100;
		Game.Health -= 10;

	}

	private void move(){
		locY += 5;
	}
	
	public int getSector() {
		return sector;
	}

	private void calculateCurrentSector(){//determins the rocks current sector. Because x values do not change we can adjust the sector by adding 3 to the current sector
		if(locY > 0)
			sector = sectorColumn;
		if(locY > 250)
			sector = sectorColumn + 3;
		if(locY > 500)
			sector = sectorColumn + 6;
			
	}
	
	private void calculateInitialSector(){//determins the sector the rock started at and sets it
		if(locX < 333){
			sectorColumn = 1;
			return;
		}
		if(locX < 666){
			sectorColumn = 2;
			return;
		}
		sectorColumn = 3;
	}
	
	public boolean getIsBelowScreen(){
		return isBelowScreen;
	}
	
	private boolean setIsBelowScreen(){//if the rock as fallen below the spaceship
		if(locY > 760)//not 750(the height of the window) because we dont want the rock to hit the bottom of the screen and disappear we want it to appear as if its going off screen
			isBelowScreen = true;
		return false;
	}
	
	public void setDebugMode(){
		debugMode = true;
	}
	
	public void drawDebugCircles(Graphics g){//only used for developing game

		switch (rockNumber) {

        case 0:  g.setColor(Color.blue);
        	     g.drawOval(locX, locY, 55, 55);
        	     g.drawString("Sector " + sector, locX, locY);
        	     break;
                 
        case 1:  g.setColor(Color.yellow);
        		 g.drawOval(locX, locY, 52, 52);
        		 g.drawString("Sector " + sector, locX, locY);
                 break;
                 
        case 2:  g.setColor(Color.green);
        		 g.drawOval(locX, locY, 36, 36);
        		 g.drawString("Sector " + sector, locX, locY);
                 break;
                 
                 
        case 3:  g.setColor(Color.pink);
        		 g.drawOval(locX, locY, 30, 30);
        		 g.drawString("Sector " + sector, locX, locY);
                 break;
                 
                 
        case 4:  g.setColor(Color.red);
        		 g.drawOval(locX, locY, 27, 27);
        		 g.drawString("Sector " + sector, locX, locY);
                 break;
                 
                 
        case 5:  g.setColor(Color.white);
        		 g.drawOval(locX, locY, 18, 18);
        		 g.drawString("Sector " + sector, locX, locY);
        		 break;
		}
        
               
	}

	public static void setStaticRocks() {

		staticRocks = true;
	}

	@Override
	public void tick() {
		setIsBelowScreen();
		calculateCurrentSector();
		
		if(!staticRocks)
			move();
		
	}


	@Override
	public void render(Graphics g) {

		if(!isCollided)
			g.drawImage(rockImage, locX, locY, null);
		if(debugMode){
			drawDebugCircles(g);

		}

	}

}
