import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Player extends GameObject implements KeyListener {
	
	private static BufferedImage spaceShipImage;
	Game game;
	LinkedList<GameObject> object = new LinkedList<GameObject>();

//	Controller controller;
	
	private int health = 100;
	private int locX = 500;
	private int locY = 670;
	private boolean isCollided = false;

	private int sector = 8;
	
	private final int radius = 20;

	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean upPressed = false;
	private boolean downPressed = false;
	
	private boolean debugMode = true;


	public Player(int x, int y, ID id, Game game) {
		super(x, y, id);
		this.game = game;

	}
//	public Player(int x, int y, ID id, Game game, Controller controller) {
//		super(x, y, id);
//		this.game = game;
//		this.controller = controller;
//
//	}


	public int getHealth() {
		return health;
	}


	public void setHealth(int health) {
		this.health = health;
	}



	public void setSpaceShipImage(BufferedImage spaceShipImage) {
		Player.spaceShipImage = spaceShipImage;
	}
	
	public int getLocY() {
		return locY;
	}


	public int getLocX() {
		return locX;
	}


	public int getRadius() {
		return radius;
	}


	public void move(){
		if(leftPressed && locX > 20)
			locX-=15;
		if(rightPressed && locX < 950)
			locX+=15;
		if(upPressed && locY > 10)
			locY -= 15;
		if(downPressed && locY < 650)
			locY += 15;
	}
	
	private void setKeyPressed(KeyEvent e){//
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			leftPressed = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightPressed = true;
		if(e.getKeyCode() == KeyEvent.VK_UP)
			upPressed = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			downPressed = true;
	}
	
	private void setKeyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			leftPressed = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightPressed = false;
		if(e.getKeyCode() == KeyEvent.VK_UP)
			upPressed = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			downPressed = false;
	}
	
	public int getSector() {
		return sector;
	}


	private void calculateSector(){//because the space ship can be in any sector we must check all of them
		if(locX < 333){
			if(locY < 250){
				sector = 1;
				return;
			}
			if(locY < 500){
				sector = 4;
				return;
			}
			sector = 7;
			return;
		}
		if(locX < 666){
			if(locY < 250){
				sector = 2;
				return;
			}
			if(locY < 500){
				sector = 5;
				return;
			}
			sector = 8;
			return;
		}
		
		if(locY < 250){
				sector = 3;
				return;
		}
		if(locY < 500){
				sector = 6;
				return;
		}
		sector = 9;
		return;
		}
		
	
	
	public void setDebugMode() {
		debugMode = true;
	}

	public void setCollided() {
		isCollided = true;
		locX = -100;// we do this because the program will stop drawing the rock but it still may be in its location for a second or two causing collisions when it should
		locY = -100;
	}
	@Override
	public void tick() {
		move();
		calculateSector();


	}



    @Override
	public void render(Graphics g) {
		g.drawImage(spaceShipImage, locX, locY, null);
		
		if(debugMode){
			g.setColor(Color.red);
			g.drawOval(locX,locY,50,50);
			g.drawString("Sector " + sector, locX, locY);
		}


	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		setKeyPressed(e);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		setKeyReleased(e);

		
	}

}
