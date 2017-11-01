import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;
public class Game extends Canvas implements Runnable {
	/**
	 *
	 *I DID NOT DO THIS GAME ALONE I DID IT WITH TWO OTHER PEOPLE
	 *CREDIT GOES TO THEM FOR THEIR PARTS
	 *
	 */

	private static BufferedImage background = null;

    public static int GAME_WIDTH = 1280;
    private int fps;
    JFrame frame;
    public int highscore = 0;
    public int currentscore = 0;


	private static final long serialVersionUID = -1442798787354930462L;

	public static final int WIDTH = 1000, HEIGHT = 750;//creates aspect ratio for window

    public static int Health = 100 * 2;
	
	static Game game;
	static Window window;
	
	public static Images images;
	private BufferedImage backGround = null;
    float scrollFactor = 0.5f; //Moves the background 0.5 pixels up per call of the draw method

	private Thread thread;
	private boolean running = false;
	
	private Handler handler;
	
	private Player player;
	private boolean debugMode = true;//the switch for debug mode.
	
	private Rock[] rockArray = {null,null,null,null,null,
								null,null,null,null,null };//where all of our rocks will be stored


    public Game(){
		handler = new Handler();
        BufferedImageLoader loader = new BufferedImageLoader();
        background = loader.loadImage("/background/space0.jpg");


		window = new Window(WIDTH, HEIGHT, "Sectors!", this);
		
		images = new Images();

		player = new Player(0,0,ID.Player, this);

		player.setSpaceShipImage(Images.getSpaceShip());//gets the spaceship image from the images class
		handler.addObject(player);
		addKeyListener(player);
		
		createInitialRocks();
		//rockTester();//test method. Comment out
		
		
		this.requestFocus();//makes it so that the user doesn't need to click the window to begin.

        start();


	}




    public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;

		//createInitialRocks();//must be here so we do not get a null pointer.
	}
	
	public synchronized void stop(){
		try{
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

//    private void init()
//    {
//        BufferedImageLoader loader = new BufferedImageLoader();
//        background = loader.loadImage("/background/space0.jpg");
//
//    }



	public void run(){

//	    init();
	    this.requestFocus();


        long lastTime = System.nanoTime();
        long lastTimer1 = System.currentTimeMillis();

        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;


        while(running){
			if(Game.Health == 0){
				highscore = currentscore;

				new EndGamePanel(true);
				frame.setVisible(false);
				frame.dispose();
				stop();
			}
        	long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1){
            	tick();
                delta--;
            }
           if(running)
        	   render();
           frames++;

           if(System.currentTimeMillis() - timer > 1000){
        	   timer += 1000;
			   fps = frames;

			   System.out.println("FPS: "+ frames);
               frames = 0;


           }


        }


	}


	public void createInitialRocks(){//creates the first 10 rocks so we do not get a null pointer.
		for(int i = 0; i < rockArray.length; i++){
			 createRocks(i);
			 addRocks(i);
		}
	}
	
	public void createRocks(int index){//creates rock objects, based on the index provided in the parameter. it saves over previous rocks that are below the screen
		Random rand = new Random();
		int x = rand.nextInt(750) + 150;
		int y = rand.nextInt(500) + 1;//so they dont all fall together
		int rockImageIndex = rand.nextInt(6);//used to assign the rock a random image index
		y = 0 - y;//spawns the rocks at negative numbers
		rockArray[index] = new Rock(x,y,ID.Rock);
		rockArray[index].setRockImage(Images.getRocks(rockImageIndex));
		rockArray[index].setRockNumber(rockImageIndex);
			
	}
	
	public void addRocks(int index){//puts the rocks into the handler
			handler.addObject(rockArray[index]);
	}
	

	public void removeRocks(int index){//removes enemies from handler
			handler.removeObject(rockArray[index]);
			
	}

	
	public int findBelowScreenRocks(){//returns the index of a rock that is below the screen
		for(int i = 0; i < rockArray.length; i++)
			if(rockArray[i].getIsBelowScreen())//checks if any of the rocks are below the screen and if they are returns there index, if none are below the screen it returns -1
				return i;
		return -1;
	}
	
	public void rockManager(){//handles checking if the rocks are on screen, removes , and adds rocks to the handler, and creates new rocks;
		int index = findBelowScreenRocks();
		if(index != -1){
			removeRocks(index);
			createRocks(index);
			addRocks(index);
		}
	}
	
	public double calculateDistance(int x1, int x2, int y1, int y2){//calculates distance between two objects
		x1 += 25;//because the top left corner of the player image is technically the center we shift it for a more accurate hit detection
		y1 += 25;
		double distance = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		return distance;
	}
	
	public double addRadii(Rock rock){//addes the radiuses
		return player.getRadius() + rock.getRadius();
	}
	
//Collision detection
	
	public void collisionDetection(){//checks if any objects are colliding then removes them if they are
		for(int i = 0; i < rockArray.length; i++){
			if(player.getSector() == rockArray[i].getSector())
				if(collisionOccured(rockArray[i]))
					removeCollidedRock(rockArray[i]);
		}

	}
	public boolean collisionOccured(Rock rock){//tests if a collision occured between a player and a rock
		double distance = calculateDistance(player.getLocX(),rock.getLocX(),player.getLocY(),rock.getLocY());
		double radiiSum = addRadii(rock);
		return distance <= radiiSum;
	}
	
	public void removeCollidedRock(Rock rock){//removes rocks after the player collides with them
		rock.setCollided();
	}
	
	
	private void drawMap(Graphics g){

		g.setColor(new Color(4,12,25));//draws wall
		g.fillRect(0, 0, 1000, 750);
        g.drawImage(background,0,0,getWidth(),getHeight(), null);
    }


//	private void drawHUD(Graphics g){
//
//	}
	

	private void setDebug(){//sets all the objects to debug mode;
		if(player != null)
			player.setDebugMode();
		
		for(int i = 0; i < rockArray.length; i++){
			if(rockArray[i] != null)
				rockArray[i].setDebugMode();
		}
	}
	
	private void rockTester(){//creates of of every size rock for testing. rocks will also not move; TEST METHOD
		Rock r0 = new Rock(100,200,ID.Rock);
		r0.setRockNumber(0);
		Rock r1 = new Rock(250,200,ID.Rock);
		r1.setRockNumber(1);
		Rock r2 = new Rock(500,200,ID.Rock);
		r2.setRockNumber(2);
		Rock r3 = new Rock(750,200,ID.Rock);
		r3.setRockNumber(3);
		Rock r4 = new Rock(100,500,ID.Rock);
		r4.setRockNumber(4);
		Rock r5 = new Rock(200,500,ID.Rock);
		r5.setRockNumber(5);
		
		Rock.setStaticRocks();
		Rock.setStaticRocks();
		Rock.setStaticRocks();
		Rock.setStaticRocks();
		Rock.setStaticRocks();
		Rock.setStaticRocks();
		
		handler.addObject(r0);
		handler.addObject(r1);
		handler.addObject(r2);
		handler.addObject(r3);
		handler.addObject(r4);
		handler.addObject(r5);
		
	}



	private void debugMap(Graphics g){//debugs the map, shows the sectors
		g.setColor(Color.ORANGE);
		g.drawLine(333, 0, 333, 750);//verticle lines
		g.drawLine(666, 0, 666, 750);//verticle lines lines
		g.drawLine(0, 250, 1000, 250);//horizontal lines
		g.drawLine(0, 500, 1000, 500);//horizontal lines
		
		g.drawString("Sector 1", 150,  50);
		g.drawString("Sector 2", 483, 50);
		g.drawString("Sector 3", 806, 50);
		g.drawString("Sector 4", 150, 300);
		g.drawString("Sector 5", 483, 300);
		g.drawString("Sector 6", 806, 300);
		g.drawString("Sector 7", 150, 550);
		g.drawString("Sector 8", 483, 550);
		g.drawString("Sector 9", 806, 550);

	}
	
	private void tick(){
		handler.tick();
		rockManager();
		collisionDetection();

//        if(Game.Health == 0){
//            highscore = currentscore;
//
//            new EndGamePanel(true);
//            frame.dispose();
//            stop();
//        }
	}



	private void render(){
		BufferStrategy bs = this.getBufferStrategy();//tripple buffer
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();




		drawMap(g);

/*health bar*/

		g.setColor(Color.gray);
		g.fillRect(5,45,200,25);

        g.setColor(Color.green);
        g.fillRect(5,45,Health,25);

        g.setColor(Color.white);
        g.drawRect(5,45,Health,25);

		handler.render(g);


/* calculate score*/


        if (Game.Health != 0){
            currentscore = currentscore + 1;
        }

        Font font = new Font("", Font.PLAIN, 12);
        g.setFont(font);
        String msg = "FPS: " + fps;
        g.setColor(Color.BLACK);
        g.drawString(msg, 11, 11);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(msg, 10, 10);

        msg = "Height: " + currentscore;
        g.setColor(Color.BLACK);
        g.drawString(msg, 100, 11);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(msg, 99, 10);

//        msg = "PLANET: Mars"  ;
//        g.setColor(Color.BLACK);
//        g.drawString(msg, 100, 26);
//        g.setColor(Color.LIGHT_GRAY);
//        g.drawString(msg, 99, 25);

        msg = "HIGH SCORE: " + highscore;
        g.setColor(Color.BLACK);
        g.drawString(msg, GAME_WIDTH/2, 11);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(msg, GAME_WIDTH/2, 10);

		if(debugMode){
			setDebug();
			debugMap(g);
		}


		g.dispose();
		bs.show();


	}






//	public static void main(String args[]){
//    	game = new Game();
//    }


}
